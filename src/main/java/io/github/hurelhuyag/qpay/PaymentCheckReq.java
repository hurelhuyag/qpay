package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentCheckReq(
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId,
    Offset offset
) {

}

