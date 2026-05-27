package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response of {@code GET /v2/payment/{id}} (payment_get).
 *
 * @param id payment_id, QPay-ээс үүссэн гүйлгээний дугаар
 * @param status payment_status (NEW, FAILED, PAID, PARTIAL, REFUNDED)
 * @param amount payment_amount, гүйлгээний нийт дүн
 * @param fee payment_fee, гүйлгээний шимтгэлийн дүн
 * @param currency payment_currency, гүйлгээний валют
 * @param date payment_date, гүйлгээ хийгдсэн хугацаа
 * @param wallet payment_wallet, гүйлгээ хийгдэн воллет, апп
 * @param transactionType transaction_type (P2P: дансны гүйлгээ, CARD: картын гүйлгээ)
 * @param objectType object_type
 * @param objectId object_id
 * @param nextPaymentDate next_payment_date, subscription payment холболт хийгдсэн үед ажиллана
 * @param nextPaymentDatetime next_payment_datetime, subscription payment холболт хийгдсэн үед ажиллана
 * @param cardTransactions card_transactions, картын гүйлгээний мэдээлэл
 * @param p2pTransactions p2p_transactions, дансны гүйлгээний мэдээлэл
 */
public record PaymentGetRes(
    @JsonProperty("payment_id") String id,
    @JsonProperty("payment_status") String status,
    @JsonProperty("payment_amount") BigDecimal amount,
    @JsonProperty("payment_fee") BigDecimal fee,
    @JsonProperty("payment_currency") String currency,
    @JsonProperty("payment_date") Instant date,
    @JsonProperty("payment_wallet") String wallet,
    @JsonProperty("transaction_type") String transactionType,
    @JsonProperty("object_type") ObjectType objectType,
    @JsonProperty("object_id") String objectId,
    @JsonProperty("next_payment_date") String nextPaymentDate,
    @JsonProperty("next_payment_datetime") String nextPaymentDatetime,
    @JsonProperty("card_transactions") List<CardTransaction> cardTransactions,
    @JsonProperty("p2p_transactions") List<P2pTransaction> p2pTransactions
) {

    public record CardTransaction(
        @JsonProperty("card_merchant_code") String cardMerchantCode,
        @JsonProperty("card_terminal_code") String cardTerminalCode,
        @JsonProperty("card_number") String cardNumber,
        @JsonProperty("card_type") String cardType,
        @JsonProperty("is_cross_border") boolean crossBorder,
        @JsonProperty("transaction_amount") BigDecimal transactionAmount,
        @JsonProperty("transaction_currency") String transactionCurrency,
        @JsonProperty("transaction_date") Instant transactionDate,
        @JsonProperty("transaction_status") String transactionStatus,
        @JsonProperty("settlement_status") String settlementStatus,
        @JsonProperty("settlement_status_date") Instant settlementStatusDate
    ) {}

    public record P2pTransaction(
        @JsonProperty("transaction_bank_code") String transactionBankCode,
        @JsonProperty("account_bank_code") String accountBankCode,
        @JsonProperty("account_bank_name") String accountBankName,
        @JsonProperty("account_number") String accountNumber,
        @JsonProperty("status") String status,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("settlement_status") String settlementStatus
    ) {}
}
