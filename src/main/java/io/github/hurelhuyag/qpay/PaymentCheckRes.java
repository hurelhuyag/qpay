package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record PaymentCheckRes(
    int count,
    @JsonProperty("paid_amount") BigDecimal paidAmount,
    @JsonProperty("rows") List<Payment> payments
) {

}

   