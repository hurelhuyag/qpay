package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record Payment(
    @JsonProperty("payment_id") String id,
    @JsonProperty("payment_id") String status,
    @JsonProperty("payment_date") Instant date,
    @JsonProperty("payment_fee") BigDecimal fee,
    @JsonProperty("payment_amount") BigDecimal amount,
    @JsonProperty("payment_currency") String currency,
    @JsonProperty("payment_wallet") String wallet,
    @JsonProperty("payment_name") String name,
    @JsonProperty("payment_description") String description,
    @JsonProperty("transaction_type") String transactionType,
    @JsonProperty("qr_code") String qrCode,
    @JsonProperty("paid_by") String paidBy,
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId
) {}