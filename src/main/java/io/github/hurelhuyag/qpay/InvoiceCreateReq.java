package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @param invoiceCode qpay-ээс өгсөн нэхэмжлэхийн код
 * @param senderInvoiceNo Байгууллагаас үүсгэх давтагдашгүй нэхэмжлэлийн дугаар
 * @param senderInvoiceCode Байгууллагын нэхэмжлэхийг хүлээн авч буй харилцагчийн дахин давтагдашгүй дугаар
 * @param invoiceReceiverCode
 * @param invoiceReceiverData
 * @param invoiceDescription Нэхэмжлэлийн утга
 * @param invoiceDueDate
 * @param allowPartial
 * @param minimumAmount
 * @param allowExceed
 * @param maximumAmount
 * @param amount Мөнгөн дүн
 * @param callbackUrl Төлбөр төлсөгдсөн эсэх талаар мэдэгдэл авах URL
 * @param note
 * @param lines
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceCreateReq(
    @JsonProperty("invoice_code") String invoiceCode,
    @JsonProperty("sender_invoice_no") String senderInvoiceNo,
    @JsonProperty("sender_invoice_code") String senderInvoiceCode,
    @JsonProperty("invoice_receiver_code") String invoiceReceiverCode,
    @JsonProperty("invoice_receiver_data") ReceiverInvoiceData invoiceReceiverData,
    @JsonProperty("invoice_description") String invoiceDescription,
    @JsonProperty("invoice_due_date") String invoiceDueDate,
    @JsonProperty("allow_partial") boolean allowPartial,
    @JsonProperty("minimum_amount") @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal minimumAmount,
    @JsonProperty("allow_exceed") boolean allowExceed,
    @JsonProperty("maximum_amount") @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal maximumAmount,
    @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal amount,
    @JsonProperty("callback_url") String callbackUrl,
    String note,
    List<Line> lines
) {

    record ReceiverInvoiceData(
        String register,
        String name,
        String email,
        String phone
    ) {}

    record Line(
        String taxProductCode,
        String lineDescription,
        BigDecimal lineQuantity,
        BigDecimal lineUnitPrice,
        String note,
        List<Discount> discounts,
        List<Surcharge> surcharges,
        List<Tax> taxes
    ) {}

    record Discount(
        String discountCode,
        String description,
        int amount,
        String note
    ) {}

    record Surcharge(
        String surchargeCode,
        String description,
        int amount,
        String note
    ) {}

    record Tax(
        String taxCode,
        String description,
        int amount,
        String note
    ) {}
}
