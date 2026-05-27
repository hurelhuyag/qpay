package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request of {@code POST /v2/payment/check} (payment_check).
 *
 * @param objectType object_type, обьектын төрөл (INVOICE, QR, ITEM)
 * @param objectId object_id, обьектын ID. Нэхэмжлэх үүсгэхэд response-д ирсэн invoice ID
 * @param offset offset, хуудаслалт
 */
public record PaymentCheckReq(
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId,
    Offset offset
) {

}
