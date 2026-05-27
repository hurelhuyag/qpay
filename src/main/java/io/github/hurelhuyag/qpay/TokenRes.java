package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response of {@code POST /v2/auth/token} and {@code POST /v2/auth/refresh} (token).
 *
 * @param tokenType token_type, токены төрөл (bearer)
 * @param refreshTokenExpiresIn refresh_expires_in, refresh токены дуусах хугацаа (timestamp)
 * @param refreshToken refresh_token, access токен сунгахдаа ашиглана
 * @param accessToken access_token, хандах эрх буюу token
 * @param expiresIn expires_in, access токены дуусах хугацаа (timestamp)
 * @param scope scope, хамрах хүрээ
 * @param notBeforePolicy not-before-policy, өмнөх токеноо хүчингүй болгож болзошгүйг анхааруулна
 * @param sessionState session_state, токен авсан төлөв
 */
public record TokenRes(
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("refresh_expires_in") long refreshTokenExpiresIn,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("expires_in") long expiresIn,
    String scope,
    @JsonProperty("not-before-policy") int notBeforePolicy,
    @JsonProperty("session_state") String sessionState
) {

    public boolean isExpired() {
        return expiresIn <= (System.currentTimeMillis() / 1000 + 5);
    }
}
