package io.github.hurelhuyag.qpay;

public record PaymentListRes(
    String paymentId,
    String paymentDate,
    String paymentStatus,
    String paymentFee,
    String paymentAmount,
    String paymentCurrency,
    String paymentWallet,
    String paymentName,
    String paymentDescription,
    String qrCode,
    String paidBy,
    String objectType,
    String objectId
) {

}