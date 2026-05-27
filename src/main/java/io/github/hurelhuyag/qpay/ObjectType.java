package io.github.hurelhuyag.qpay;

/**
 * Обьектын төрөл. Used as {@code object_type} across the payment endpoints.
 */
public enum ObjectType {

    /** Байгууллага */
    MERCHANT,

    /** Нэхэмжлэх */
    INVOICE,

    /** QR код */
    QR,

    /** Бүтээгдэхүүн */
    ITEM,

    /** Гүйлгээ */
    PAYMENT
}
