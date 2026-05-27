package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request of {@code POST /v2/payment/list} (payment_list).
 *
 * @param objectType object_type, обьектын төрөл (MERCHANT, INVOICE, QR)
 * @param objectId object_id, обьектын ID. Обьектын төрөл INVOICE үед нэхэмжлэхийн код (invoice_code)
 * @param startDate start_date, гүйлгээ эхлэх цаг
 * @param endDate end_date, гүйлгээ дуусах цаг
 * @param offset offset, хуудаслалт
 */
public record PaymentListReq(
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId,
    @JsonProperty("start_date") String startDate,
    @JsonProperty("end_date") String endDate,
    Offset offset
) {

}
