package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Request of {@code POST /v2/invoice} (invoice_create).
 *
 * @param invoiceCode qpay-ээс өгсөн нэхэмжлэхийн код
 * @param senderInvoiceNo Байгууллагаас үүсгэх давтагдашгүй нэхэмжлэлийн дугаар
 * @param invoiceReceiverCode нэхэмжлэх хүлээн авагчийн код
 * @param invoiceReceiverData нэхэмжлэх хүлээн авагчийн мэдээлэл
 * @param invoiceDescription Нэхэмжлэлийн утга
 * @param invoiceDueDate нэхэмжлэлийн төлөх хугацаа
 * @param expiryEnabled enable_expiry, дуусах хугацаа идэвхжүүлэх эсэх
 * @param allowPartial хэсэгчлэн төлөхийг зөвшөөрөх эсэх
 * @param minimumAmount төлөх доод дүн
 * @param allowExceed илүү төлөхийг зөвшөөрөх эсэх
 * @param maximumAmount төлөх дээд дүн
 * @param amount Мөнгөн дүн
 * @param callbackUrl Төлбөр төлсөгдсөн эсэх талаар мэдэгдэл авах URL
 * @param note тэмдэглэл
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceCreateReq(
    @JsonProperty("invoice_code") String invoiceCode,
    @JsonProperty("sender_invoice_no") String senderInvoiceNo,
    @JsonProperty("invoice_receiver_code") String invoiceReceiverCode,
    @JsonProperty("invoice_receiver_data") ReceiverInvoiceData invoiceReceiverData,
    @JsonProperty("invoice_description") String invoiceDescription,
    @JsonProperty("invoice_due_date") String invoiceDueDate,
    @JsonProperty("enable_expiry") boolean expiryEnabled,
    @JsonProperty("allow_partial") boolean allowPartial,
    @JsonProperty("minimum_amount") @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal minimumAmount,
    @JsonProperty("allow_exceed") boolean allowExceed,
    @JsonProperty("maximum_amount") @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal maximumAmount,
    @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal amount,
    @JsonProperty("callback_url") String callbackUrl,
    String note
) {

    /**
     * @param register нэхэмжлэх хүлээн авагчийн регистр
     * @param name нэр
     * @param email и-мэйл
     * @param phone утасны дугаар
     */
    record ReceiverInvoiceData(
        String register,
        String name,
        String email,
        String phone
    ) {}
}
