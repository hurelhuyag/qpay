package io.github.hurelhuyag.qpay;

/**
 * Error body of {@code DELETE /v2/payment/cancel/{id}} and {@code DELETE /v2/payment/refund/{id}}
 * (payment_cancel, payment_refund). Амжилттай үед HTTP 200, амжилтгүй үед энэ бие буцна.
 *
 * @param error алдааны код (жишээ нь PAYMENT_SETTLED)
 * @param message алдааны мессеж
 */
public record PaymentCancelRes(
    String error,
    String message
) {}

