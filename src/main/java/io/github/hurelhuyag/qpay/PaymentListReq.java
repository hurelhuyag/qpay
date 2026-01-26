package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentListReq(
    @JsonProperty("merchant_id") String merchantId,
    Offset offset
) {

}