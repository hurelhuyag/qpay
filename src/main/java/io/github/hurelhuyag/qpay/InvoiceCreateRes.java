package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @param invoiceId
 * @param qrText Данс болон картын гүйлгээ дэмжих QR утга
 * @param qrImage Base64 зурган QR код Qpay лого голдоо агуулсан
 * @param urls аппликейшнээс банкны аппликейшнруу үсрэх Deeplink
 */
public record InvoiceCreateRes(
    @JsonProperty("invoice_id") String invoiceId,
    @JsonProperty("qr_text") String qrText,
    @JsonProperty("qr_image") String qrImage,
    @JsonProperty("qPay_shortUrl") String qpayShortUrl,
    List<QpayUrl> urls
) {

    record QpayUrl(
        String name,
        String description,
        String logo,
        String link
    ) {}
}