package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response of {@code POST /v2/invoice} (invoice_create).
 *
 * @param invoiceId invoice_id, үүсгэсэн нэхэмжлэхийн код
 * @param qrText Данс болон картын гүйлгээ дэмжих QR утга
 * @param qrImage Base64 зурган QR код Qpay лого голдоо агуулсан
 * @param qpayShortUrl qPay_shortUrl, төлбөрийн богино URL
 * @param urls аппликейшнээс банкны аппликейшнруу үсрэх Deeplink
 */
public record InvoiceCreateRes(
    @JsonProperty("invoice_id") String invoiceId,
    @JsonProperty("qr_text") String qrText,
    @JsonProperty("qr_image") String qrImage,
    @JsonProperty("qPay_shortUrl") String qpayShortUrl,
    List<QpayUrl> urls
) {

    /**
     * @param name банкны апп-ын нэр
     * @param description тайлбар
     * @param logo лого зургийн URL
     * @param link банкны апп руу үсрэх deeplink
     */
    record QpayUrl(
        String name,
        String description,
        String logo,
        String link
    ) {}
}