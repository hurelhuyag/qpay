package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body of {@code DELETE /v2/payment/cancel/{id}} and {@code DELETE /v2/payment/refund/{id}}
 * (payment_cancel, payment_refund).
 *
 * @param callbackUrl callback_url, төлбөр буцаасан тухай мэдэгдэл авах URL
 * @param note note, тэмдэглэл
 */
public record PaymentCancelReq(
    @JsonProperty("callback_url") String callbackUrl,
    String note
) {}
