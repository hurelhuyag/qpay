package io.github.hurelhuyag.qpay;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

import java.util.Base64;

public class QpayFeignConfig {

    private volatile TokenRes tokenRes;

    private final QpayAuthApi authApi;
    private final QpayProperties config;

    public QpayFeignConfig(QpayAuthApi authApi, QpayProperties config) {
        this.authApi = authApi;
        this.config = config;
    }

    /*@Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }*/

    private void obtainToken() {
        var credentials = Base64.getEncoder().encodeToString((config.login() + ":" + config.password()).getBytes());
        tokenRes = authApi.getToken("Basic " + credentials);
    }

    private void refreshToken() {
        tokenRes = authApi.refreshToken("Bearer " + tokenRes.refreshToken());
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (tokenRes == null) {
                synchronized (this) {
                    if (tokenRes == null) {
                        obtainToken();
                    }
                }
            } else {
                if (tokenRes.isExpired()) {
                    synchronized (this) {
                        if (tokenRes.isExpired()) {
                            refreshToken();
                        }
                    }
                }
            }
            requestTemplate.header("Authorization", "Bearer " + tokenRes.accessToken());
        };
    }

}
