package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response of {@code GET /v2/invoice/{id}} (нэхэмжлэхийн дэлгэрэнгүй мэдээлэл).
 *
 * @param id invoice_id, qpay-ээс өгсөн нэхэмжлэхийн код
 * @param status invoice_status, нэхэмжлэлийн төлөв
 * @param senderInvoiceNo sender_invoice_no, байгууллагаас үүсгэсэн давтагдашгүй нэхэмжлэлийн дугаар
 * @param senderBranchCode sender_branch_code, салбарын код
 * @param senderBranchData sender_branch_data, салбарын мэдээлэл
 * @param senderStaffCode sender_staff_code, ажилтны код
 * @param senderStaffData sender_staff_data, ажилтны мэдээлэл
 * @param senderTerminalCode sender_terminal_code, терминалын код
 * @param senderTerminalData sender_terminal_data, терминалын мэдээлэл
 * @param description invoice_description, нэхэмжлэлийн утга
 * @param dueDate invoice_due_date, нэхэмжлэлийн төлөх хугацаа
 * @param expiryEnabled enable_expiry, дуусах хугацаа идэвхжсэн эсэх
 * @param expireAt expiry_date, дуусах огноо
 * @param allowPartial allow_partial, хэсэгчлэн төлөхийг зөвшөөрөх эсэх
 * @param minimumAmount minimum_amount, төлөх доод дүн
 * @param allowExceed allow_exceed, илүү төлөхийг зөвшөөрөх эсэх
 * @param maximumAmount maximum_amount, төлөх дээд дүн
 * @param totalAmount total_amount, нийт дүн
 * @param grossAmount gross_amount, нийт үнийн дүн
 * @param taxAmount tax_amount, татварын дүн
 * @param surchargeAmount surcharge_amount, нэмэгдэл хураамжийн дүн
 * @param discountAmount discount_amount, хөнгөлөлтийн дүн
 * @param callbackUrl callback_url, төлбөр төлөгдсөн эсэх талаар мэдэгдэл авах URL
 * @param note note, тэмдэглэл
 * @param lines lines, нэхэмжлэлийн мөрүүд
 * @param transactions transactions, гүйлгээнүүд
 * @param inputs inputs, оролтууд
 */
public record Invoice(
        @JsonProperty("invoice_id") String id,
        @JsonProperty("invoice_status") String status,
        @JsonProperty("sender_invoice_no") String senderInvoiceNo,
        @JsonProperty("sender_branch_code") String senderBranchCode,
        @JsonProperty("sender_branch_data") Object senderBranchData,
        @JsonProperty("sender_staff_code") String senderStaffCode,
        @JsonProperty("sender_staff_data") String senderStaffData,
        @JsonProperty("sender_terminal_code") String senderTerminalCode,
        @JsonProperty("sender_terminal_data") String senderTerminalData,
        @JsonProperty("invoice_description") String description,
        @JsonProperty("invoice_due_date") Instant dueDate,
        @JsonProperty("enable_expiry") boolean expiryEnabled,
        @JsonProperty("expiry_date") Instant expireAt,
        @JsonProperty("allow_partial") boolean allowPartial,
        @JsonProperty("minimum_amount") BigDecimal minimumAmount,
        @JsonProperty("allow_exceed") boolean allowExceed,
        @JsonProperty("maximum_amount") BigDecimal maximumAmount,
        @JsonProperty("total_amount") BigDecimal totalAmount,
        @JsonProperty("gross_amount") BigDecimal grossAmount,
        @JsonProperty("tax_amount") BigDecimal taxAmount,
        @JsonProperty("surcharge_amount") BigDecimal surchargeAmount,
        @JsonProperty("discount_amount") BigDecimal discountAmount,
        @JsonProperty("callback_url") String callbackUrl,
        @JsonProperty("note") String note,
        @JsonProperty("lines") List<Line> lines,
        @JsonProperty("transactions") List<Transaction> transactions,
        @JsonProperty("inputs") List<Input> inputs
) {

    /**
     * @param taxProductCode tax_product_code, бүтээгдэхүүний татварын код
     * @param lineDescription line_description, мөрийн тайлбар
     * @param lineQuantity line_quantity, тоо хэмжээ
     * @param lineUnitPrice line_unit_price, нэгж үнэ
     * @param note note, тэмдэглэл
     * @param discounts discounts, хөнгөлөлтүүд
     * @param surcharges surcharges, нэмэгдэл хураамжууд
     * @param taxes taxes, татварууд
     */
    public record Line(
            @JsonProperty("tax_product_code") String taxProductCode,
            @JsonProperty("line_description") String lineDescription,
            @JsonProperty("line_quantity") String lineQuantity,
            @JsonProperty("line_unit_price") String lineUnitPrice,
            @JsonProperty("note") String note,
            @JsonProperty("discounts") List<Object> discounts,
            @JsonProperty("surcharges") List<String> surcharges,
            @JsonProperty("taxes") List<String> taxes
    ) {}

    /** Гүйлгээний мөр (placeholder, талбарууд хараахан тодорхойлогдоогүй). */
    public record Transaction() {}

    /** Оролтын мөр (placeholder, талбарууд хараахан тодорхойлогдоогүй). */
    public record Input() {}
}
