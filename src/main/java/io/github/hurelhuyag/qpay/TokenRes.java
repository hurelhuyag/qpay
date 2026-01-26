package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

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
