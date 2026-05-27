package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * A single entry of the {@code POST /v2/payment/list} response (payment_list).
 *
 * @param id payment_id, QPay-ээс үүссэн гүйлгээний дугаар
 * @param date payment_date, гүйлгээний огноо
 * @param status payment_status (NEW, FAILED, PAID, REFUNDED)
 * @param fee payment_fee, шимтгэлийн дүн
 * @param amount payment_amount, гүйлгээний үнийн дүн
 * @param currency payment_currency, гүйлгээний валют
 * @param wallet payment_wallet, гүйлгээ хийсэн воллетийн дугаар
 * @param name payment_name, төлбөрийн нэр
 * @param description payment_description, гүйлгээний утга
 * @param qrCode qr_code, гүйлгээнд ашиглагдсан QR код
 * @param paidBy paid_by (P2P: дансны гүйлгээ, CARD: картын гүйлгээ)
 * @param objectType object_type
 * @param objectId object_id
 */
public record PaymentListRes(
    @JsonProperty("payment_id") String id,
    @JsonProperty("payment_date") Instant date,
    @JsonProperty("payment_status") String status,
    @JsonProperty("payment_fee") BigDecimal fee,
    @JsonProperty("payment_amount") BigDecimal amount,
    @JsonProperty("payment_currency") String currency,
    @JsonProperty("payment_wallet") String wallet,
    @JsonProperty("payment_name") String name,
    @JsonProperty("payment_description") String description,
    @JsonProperty("qr_code") String qrCode,
    @JsonProperty("paid_by") String paidBy,
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId
) {

}
