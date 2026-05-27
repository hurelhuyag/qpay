package io.github.hurelhuyag.qpay;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Access token handling in {@link QpayFeignConfig#requestInterceptor()}.
 * Each test uses a fresh config instance so the cached {@code tokenRes} state is isolated.
 */
@ExtendWith(MockitoExtension.class)
class QpayFeignConfigTest {

    static final String LOGIN = "TEST_MERCHANT";
    static final String PASSWORD = "123456";
    static final String EXPECTED_BASIC =
            "Basic " + Base64.getEncoder().encodeToString((LOGIN + ":" + PASSWORD).getBytes());

    @Mock
    QpayAuthApi authApi;

    RequestInterceptor interceptor() {
        return new QpayFeignConfig(authApi, new QpayProperties(LOGIN, PASSWORD)).requestInterceptor();
    }

    static TokenRes token(String accessToken, String refreshToken, long expiresIn) {
        return new TokenRes("bearer", 9_999_999_999L, refreshToken, accessToken, expiresIn,
                "get_token", 0, "sandbox");
    }

    /** A timestamp comfortably in the future so {@link TokenRes#isExpired()} is false. */
    static long future() {
        return System.currentTimeMillis() / 1000 + 3600;
    }

    static String authHeader(RequestTemplate template) {
        return template.headers().get("Authorization").iterator().next();
    }

    @Test
    void obtainsTokenWhenNoneCached() {
        when(authApi.getToken(anyString())).thenReturn(token("access-1", "refresh-1", future()));

        var template = new RequestTemplate();
        interceptor().apply(template);

        verify(authApi).getToken(EXPECTED_BASIC);
        verify(authApi, never()).refreshToken(anyString());
        assertThat(authHeader(template)).isEqualTo("Bearer access-1");
    }

    @Test
    void reusesCachedValidToken() {
        when(authApi.getToken(anyString())).thenReturn(token("access-1", "refresh-1", future()));

        var interceptor = interceptor();
        var first = new RequestTemplate();
        interceptor.apply(first);
        var second = new RequestTemplate();
        interceptor.apply(second);

        verify(authApi, times(1)).getToken(anyString());
        verify(authApi, never()).refreshToken(anyString());
        assertThat(authHeader(first)).isEqualTo("Bearer access-1");
        assertThat(authHeader(second)).isEqualTo("Bearer access-1");
    }

    @Test
    void refreshesCachedExpiredToken() {
        when(authApi.getToken(anyString())).thenReturn(token("access-1", "refresh-1", 0L));
        when(authApi.refreshToken(anyString())).thenReturn(token("access-2", "refresh-2", future()));

        var interceptor = interceptor();
        interceptor.apply(new RequestTemplate()); // first call obtains the (already expired) token
        var afterRefresh = new RequestTemplate();
        interceptor.apply(afterRefresh);

        verify(authApi, times(1)).getToken(anyString());
        verify(authApi).refreshToken("Bearer refresh-1");
        assertThat(authHeader(afterRefresh)).isEqualTo("Bearer access-2");
    }
}
