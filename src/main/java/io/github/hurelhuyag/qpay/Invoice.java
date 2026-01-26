package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

    public record Transaction() {}
    public record Input() {}
}
