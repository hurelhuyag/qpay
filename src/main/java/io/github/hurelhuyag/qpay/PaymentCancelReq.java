package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentCancelReq(
    @JsonProperty("callback_url") String callbackUrl,
    String note
) {}

