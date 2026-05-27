package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response of {@code POST /v2/payment/check} (payment_check).
 *
 * @param count count, нийт гүйлгээний мөрийн тоо
 * @param paidAmount paid_amount, гүйлгээний дүн
 * @param payments rows, гүйлгээний мөр
 */
public record PaymentCheckRes(
    int count,
    @JsonProperty("paid_amount") BigDecimal paidAmount,
    @JsonProperty("rows") List<Payment> payments
) {

}

   