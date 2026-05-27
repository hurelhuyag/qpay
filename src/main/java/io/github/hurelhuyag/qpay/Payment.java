package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * A single {@code rows[]} entry of the {@code POST /v2/payment/check} response (payment_check).
 *
 * @param id payment_id, QPay-ээс үүссэн гүйлгээний дугаар
 * @param status payment_status (NEW, FAILED, PAID, PARTIAL, REFUNDED)
 * @param amount payment_amount, гүйлгээний нийт дүн
 * @param fee trx_fee, гүйлгээний шимтгэлийн дүн
 * @param currency payment_currency, гүйлгээний валют
 * @param wallet payment_wallet, гүйлгээ хийгдэн воллет, апп
 * @param paymentType payment_type (P2P: дансны гүйлгээ, CARD: картын гүйлгээ)
 * @param nextPaymentDate next_payment_date, subscription payment холболт хийгдсэн үед ажиллана
 * @param nextPaymentDatetime next_payment_datetime, subscription payment холболт хийгдсэн үед ажиллана
 * @param cardTransactions card_transactions, wallet буюу картын гүйлгээ хүлээн авах үед ирнэ
 * @param p2pTransactions p2p_transactions, дансны буюу банкны апп-аар гүйлгээ хүлээн авах үед ирнэ
 */
public record Payment(
    @JsonProperty("payment_id") String id,
    @JsonProperty("payment_status") String status,
    @JsonProperty("payment_amount") BigDecimal amount,
    @JsonProperty("trx_fee") BigDecimal fee,
    @JsonProperty("payment_currency") String currency,
    @JsonProperty("payment_wallet") String wallet,
    @JsonProperty("payment_type") String paymentType,
    @JsonProperty("next_payment_date") String nextPaymentDate,
    @JsonProperty("next_payment_datetime") String nextPaymentDatetime,
    @JsonProperty("card_transactions") List<CardTransaction> cardTransactions,
    @JsonProperty("p2p_transactions") List<P2pTransaction> p2pTransactions
) {

    /**
     * @param cardType card_type, картын төрөл
     * @param crossBorder is_cross_border, гадаад гүйлгээ зөвшөөрсөн эсэх
     * @param amount amount, төлбөр төлсөн үнийн дүн
     * @param currency currency, валют
     * @param date date, гүйлгээ хийгдсэн хугацаа
     * @param status status
     * @param settlementStatus settlement_status, картын гүйлгээний хаалт хийгдсэн эсэх статус
     * @param settlementStatusDate settlement_status_date, картын гүйлгээ бичигдсэн хугацаа
     */
    public record CardTransaction(
        @JsonProperty("card_type") String cardType,
        @JsonProperty("is_cross_border") boolean crossBorder,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("date") Instant date,
        @JsonProperty("status") String status,
        @JsonProperty("settlement_status") String settlementStatus,
        @JsonProperty("settlement_status_date") Instant settlementStatusDate
    ) {}

    /**
     * @param transactionBankCode transaction_bank_code, гүйлгээг эхлүүлэгч банкны код
     * @param accountBankCode account_bank_code, төлбөр хүлээн авагч банкны код
     * @param accountBankName account_bank_name, төлбөр хүлээн авагч банкны нэр
     * @param accountNumber account_number, төлбөр хүлээн авагч дансны дугаар
     * @param status status
     * @param amount amount, дансанд орсон төлбөрийн дүн
     * @param currency currency, дансанд орсон төлбөрийн валют
     * @param settlementStatus settlement_status, дансны гүйлгээний хаалт хийгдсэн эсэх статус
     */
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
