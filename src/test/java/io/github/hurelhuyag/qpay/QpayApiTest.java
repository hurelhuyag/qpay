package io.github.hurelhuyag.qpay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(
        classes = QpayApiTest.TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.cloud.openfeign.client.config.QpayApi.url=http://localhost:${wiremock.server.port}"
)
@EnableWireMock
class QpayApiTest {

    @Configuration
    @EnableAutoConfiguration
    @Import(Config.class)
    static class TestConfig {
    }

    @Autowired
    QpayApi client;

    @BeforeEach
    void stubToken() {
        // QpayFeignConfig obtains an OAuth token before each request and sends it as a Bearer header.
        stubFor(post(urlEqualTo("/v2/auth/token"))
                .willReturn(okJson("""
                {
                  "token_type": "bearer",
                  "refresh_expires_in": 9999999999,
                  "refresh_token": "test-refresh-token",
                  "access_token": "test-access-token",
                  "expires_in": 9999999999,
                  "scope": "get_token",
                  "not-before-policy": 0,
                  "session_state": "sandbox"
                }
                """)
                )
        );
    }

    @Test
    void createInvoice() {
        stubFor(post(urlEqualTo("/v2/invoice"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .withRequestBody(equalToJson("""
                {
                  "invoice_code": "TEST_INVOICE",
                  "sender_invoice_no": "1234567",
                  "invoice_receiver_code": "terminal",
                  "invoice_description": "test",
                  "enable_expiry": false,
                  "allow_partial": false,
                  "allow_exceed": false,
                  "amount": "100",
                  "callback_url": "https://example.test/callback"
                }
                """)
                )
                .willReturn(okJson("""
                {
                  "invoice_id": "584a9acd-ff03-4bea-8850-6c36e05cae3a",
                  "qr_text": "0002010102121531",
                  "qr_image": "iVBORw0KGgo=",
                  "qPay_shortUrl": "https://sandbox-s.qpay.mn/LYg42tpto",
                  "urls": [
                    {
                      "name": "qPay wallet",
                      "description": "qPay хэтэвч",
                      "logo": "https://example.test/logo.jpg",
                      "link": "qpaywallet://q?qPay_QRcode=0002"
                    }
                  ]
                }
                """)
                )
        );

        var req = new InvoiceCreateReq(
                "TEST_INVOICE", "1234567", "terminal", null, "test",
                null, false, false, null, false, null,
                BigDecimal.valueOf(100), "https://example.test/callback", null
        );

        var res = client.createInvoice(req);

        assertThat(res.invoiceId()).isEqualTo("584a9acd-ff03-4bea-8850-6c36e05cae3a");
        assertThat(res.qrText()).isEqualTo("0002010102121531");
        assertThat(res.qpayShortUrl()).isEqualTo("https://sandbox-s.qpay.mn/LYg42tpto");
        assertThat(res.urls()).hasSize(1);
        assertThat(res.urls().get(0).name()).isEqualTo("qPay wallet");
        assertThat(res.urls().get(0).link()).isEqualTo("qpaywallet://q?qPay_QRcode=0002");
    }

    @Test
    void findInvoice() {
        stubFor(get(urlEqualTo("/v2/invoice/584a9acd-ff03-4bea-8850-6c36e05cae3a"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .willReturn(okJson("""
                {
                  "invoice_id": "584a9acd-ff03-4bea-8850-6c36e05cae3a",
                  "invoice_status": "OPEN",
                  "sender_invoice_no": "1234567",
                  "invoice_description": "test",
                  "enable_expiry": false,
                  "allow_partial": false,
                  "allow_exceed": false,
                  "total_amount": 100,
                  "callback_url": "https://example.test/callback",
                  "lines": []
                }
                """)
                )
        );

        var res = client.findInvoice("584a9acd-ff03-4bea-8850-6c36e05cae3a");

        assertThat(res.id()).isEqualTo("584a9acd-ff03-4bea-8850-6c36e05cae3a");
        assertThat(res.status()).isEqualTo("OPEN");
        assertThat(res.senderInvoiceNo()).isEqualTo("1234567");
        assertThat(res.description()).isEqualTo("test");
        assertThat(res.totalAmount()).isEqualByComparingTo("100");
    }

    @Test
    void deleteInvoice() {
        stubFor(delete(urlEqualTo("/v2/invoice/584a9acd-ff03-4bea-8850-6c36e05cae3a"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .willReturn(ok())
        );

        assertThatCode(() -> client.deleteInvoice("584a9acd-ff03-4bea-8850-6c36e05cae3a"))
                .doesNotThrowAnyException();

        verify(deleteRequestedFor(urlEqualTo("/v2/invoice/584a9acd-ff03-4bea-8850-6c36e05cae3a")));
    }

    @Test
    void listPayments() {
        stubFor(post(urlEqualTo("/v2/payment/list"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .withRequestBody(equalToJson("""
                {
                  "object_type": "INVOICE",
                  "object_id": "TEST_INVOICE",
                  "start_date": "2021-09-06 00:00:01",
                  "end_date": "2021-09-06 23:59:59",
                  "offset": {
                    "page_number": 1,
                    "page_limit": 100
                  }
                }
                """)
                )
                .willReturn(okJson("""
                [
                  {
                    "payment_id": "12f94137-66fd-4d90-b2b2-8225c1b4ed2d",
                    "payment_date": "2020-10-19T08:58:46.641Z",
                    "payment_status": "PAID",
                    "payment_fee": "10",
                    "payment_amount": "1000",
                    "payment_currency": "MNT",
                    "payment_wallet": "0fc9b71c-cd87-4ffd-9cac-2279ebd9deb0",
                    "payment_name": "Юнивишн",
                    "payment_description": "Юнивишн төлбөр",
                    "qr_code": "0002010102111525",
                    "paid_by": "CARD",
                    "object_type": "INVOICE",
                    "object_id": "00f94137-66fd-4d90-b2b2-8225c1b4ed2d"
                  }
                ]
                """)
                )
        );

        var res = client.listPayments(new PaymentListReq(
                ObjectType.INVOICE, "TEST_INVOICE",
                "2021-09-06 00:00:01", "2021-09-06 23:59:59", new Offset(1, 100)
        ));

        assertThat(res).hasSize(1);
        assertThat(res.get(0).id()).isEqualTo("12f94137-66fd-4d90-b2b2-8225c1b4ed2d");
        assertThat(res.get(0).date()).isEqualTo(Instant.parse("2020-10-19T08:58:46.641Z"));
        assertThat(res.get(0).status()).isEqualTo("PAID");
        assertThat(res.get(0).fee()).isEqualByComparingTo("10");
        assertThat(res.get(0).amount()).isEqualByComparingTo("1000");
        assertThat(res.get(0).name()).isEqualTo("Юнивишн");
        assertThat(res.get(0).paidBy()).isEqualTo("CARD");
        assertThat(res.get(0).objectType()).isEqualTo(ObjectType.INVOICE);
        assertThat(res.get(0).objectId()).isEqualTo("00f94137-66fd-4d90-b2b2-8225c1b4ed2d");
    }

    @Test
    void findPayments() {
        stubFor(get(urlEqualTo("/v2/payment/493622150113497"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .willReturn(okJson("""
                {
                  "payment_id": "493622150113497",
                  "payment_status": "PAID",
                  "payment_fee": "1.00",
                  "payment_amount": "100.00",
                  "payment_currency": "MNT",
                  "payment_date": "2022-03-11T05:57:47.336Z",
                  "payment_wallet": "0fc9b71c-cd87-4ffd-9cac-2279ebd9deb0",
                  "object_type": "INVOICE",
                  "object_id": "d50f49f2-9032-4a74-8929-530531f28f63",
                  "next_payment_date": null,
                  "next_payment_datetime": null,
                  "transaction_type": "P2P",
                  "card_transactions": [],
                  "p2p_transactions": [
                    {
                      "transaction_bank_code": "050000",
                      "account_bank_code": "050000",
                      "account_bank_name": "Хаан банк",
                      "account_number": "50*******",
                      "status": "SUCCESS",
                      "amount": "99.00",
                      "currency": "MNT",
                      "settlement_status": "SETTLED"
                    }
                  ]
                }
                """)
                )
        );

        var res = client.findPayments("493622150113497");

        assertThat(res.id()).isEqualTo("493622150113497");
        assertThat(res.status()).isEqualTo("PAID");
        assertThat(res.amount()).isEqualByComparingTo("100.00");
        assertThat(res.fee()).isEqualByComparingTo("1.00");
        assertThat(res.currency()).isEqualTo("MNT");
        assertThat(res.date()).isEqualTo(Instant.parse("2022-03-11T05:57:47.336Z"));
        assertThat(res.transactionType()).isEqualTo("P2P");
        assertThat(res.objectType()).isEqualTo(ObjectType.INVOICE);
        assertThat(res.objectId()).isEqualTo("d50f49f2-9032-4a74-8929-530531f28f63");
        assertThat(res.cardTransactions()).isEmpty();
        assertThat(res.p2pTransactions()).hasSize(1);
        var p2p = res.p2pTransactions().get(0);
        assertThat(p2p.accountBankName()).isEqualTo("Хаан банк");
        assertThat(p2p.amount()).isEqualByComparingTo("99.00");
        assertThat(p2p.settlementStatus()).isEqualTo("SETTLED");
    }

    @Test
    void findPaymentsCardTransaction() {
        stubFor(get(urlEqualTo("/v2/payment/8e25b4d5-fe5a-4d0f-b050-def68a82aaad"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .willReturn(okJson("""
                {
                  "payment_id": "8e25b4d5-fe5a-4d0f-b050-def68a82aaad",
                  "payment_status": "PAID",
                  "payment_fee": "1.00",
                  "payment_amount": "100.00",
                  "payment_currency": "MNT",
                  "payment_date": "2022-03-11T06:23:49.242Z",
                  "payment_wallet": "KKTT",
                  "object_type": "INVOICE",
                  "object_id": "85dba5c9-7dbe-4e07-8937-4b4177269add",
                  "next_payment_date": null,
                  "next_payment_datetime": null,
                  "transaction_type": "CARD",
                  "card_transactions": [
                    {
                      "card_merchant_code": "WQPAYMN",
                      "card_terminal_code": "WQPAYMN",
                      "card_number": "6234 58** **** 2733",
                      "card_type": "UNIONPAY",
                      "is_cross_border": false,
                      "transaction_amount": "100.00",
                      "transaction_currency": "MNT",
                      "transaction_date": "2022-03-11T06:23:49.233Z",
                      "transaction_status": "SUCCESS",
                      "settlement_status": "PENDING",
                      "settlement_status_date": "2022-03-11T06:23:48.587Z"
                    }
                  ],
                  "p2p_transactions": []
                }
                """)
                )
        );

        var res = client.findPayments("8e25b4d5-fe5a-4d0f-b050-def68a82aaad");

        assertThat(res.transactionType()).isEqualTo("CARD");
        assertThat(res.p2pTransactions()).isEmpty();
        assertThat(res.cardTransactions()).hasSize(1);
        var card = res.cardTransactions().get(0);
        assertThat(card.cardMerchantCode()).isEqualTo("WQPAYMN");
        assertThat(card.cardNumber()).isEqualTo("6234 58** **** 2733");
        assertThat(card.cardType()).isEqualTo("UNIONPAY");
        assertThat(card.crossBorder()).isFalse();
        assertThat(card.transactionAmount()).isEqualByComparingTo("100.00");
        assertThat(card.transactionDate()).isEqualTo(Instant.parse("2022-03-11T06:23:49.233Z"));
        assertThat(card.settlementStatus()).isEqualTo("PENDING");
        assertThat(card.settlementStatusDate()).isEqualTo(Instant.parse("2022-03-11T06:23:48.587Z"));
    }

    @Test
    void checkPayments() {
        stubFor(post(urlEqualTo("/v2/payment/check"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .withRequestBody(equalToJson("""
                {
                  "object_type": "INVOICE",
                  "object_id": "584a9acd-ff03-4bea-8850-6c36e05cae3a",
                  "offset": {
                    "page_number": 1,
                    "page_limit": 100
                  }
                }
                """)
                )
                .willReturn(okJson("""
                {
                  "count": 1,
                  "paid_amount": 100,
                  "rows": [
                    {
                      "payment_id": "493622150113497",
                      "payment_status": "PAID",
                      "payment_amount": "100.00",
                      "trx_fee": "1.00",
                      "payment_currency": "MNT",
                      "payment_wallet": "Хаан банк апп",
                      "payment_type": "P2P",
                      "next_payment_date": null,
                      "next_payment_datetime": null,
                      "card_transactions": [],
                      "p2p_transactions": [
                        {
                          "transaction_bank_code": "050000",
                          "account_bank_code": "050000",
                          "account_bank_name": "Хаан банк",
                          "account_number": "50*******",
                          "status": "SUCCESS",
                          "amount": "99.00",
                          "currency": "MNT",
                          "settlement_status": "SETTLED"
                        }
                      ]
                    }
                  ]
                }
                """)
                )
        );

        var res = client.checkPayments(new PaymentCheckReq(
                ObjectType.INVOICE, "584a9acd-ff03-4bea-8850-6c36e05cae3a", new Offset(1, 100)
        ));

        assertThat(res.count()).isEqualTo(1);
        assertThat(res.paidAmount()).isEqualByComparingTo("100");
        assertThat(res.payments()).hasSize(1);
        var payment = res.payments().get(0);
        assertThat(payment.id()).isEqualTo("493622150113497");
        assertThat(payment.status()).isEqualTo("PAID");
        assertThat(payment.amount()).isEqualByComparingTo("100.00");
        assertThat(payment.fee()).isEqualByComparingTo("1.00");
        assertThat(payment.paymentType()).isEqualTo("P2P");
        assertThat(payment.cardTransactions()).isEmpty();
        assertThat(payment.p2pTransactions()).hasSize(1);
        assertThat(payment.p2pTransactions().get(0).accountBankName()).isEqualTo("Хаан банк");
        assertThat(payment.p2pTransactions().get(0).amount()).isEqualByComparingTo("99.00");
    }

    @Test
    void cancelPayments() {
        stubFor(delete(urlEqualTo("/v2/payment/cancel/P-1"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .withRequestBody(equalToJson("""
                {
                  "callback_url": "https://example.test/callback",
                  "note": "cancel"
                }
                """)
                )
                .willReturn(okJson("""
                {
                  "error": "NO_ERROR",
                  "message": "cancelled"
                }
                """)
                )
        );

        var res = client.cancelPayments("P-1", new PaymentCancelReq("https://example.test/callback", "cancel"));

        assertThat(res.error()).isEqualTo("NO_ERROR");
        assertThat(res.message()).isEqualTo("cancelled");
    }

    @Test
    void refundPayments() {
        stubFor(delete(urlEqualTo("/v2/payment/refund/P-1"))
                .withHeader("Authorization", equalTo("Bearer test-access-token"))
                .withRequestBody(equalToJson("""
                {
                  "callback_url": "https://example.test/callback",
                  "note": "refund"
                }
                """)
                )
                .willReturn(okJson("""
                {
                  "error": "NO_ERROR",
                  "message": "refunded"
                }
                """)
                )
        );

        var res = client.refundPayments("P-1", new PaymentCancelReq("https://example.test/callback", "refund"));

        assertThat(res.error()).isEqualTo("NO_ERROR");
        assertThat(res.message()).isEqualTo("refunded");
    }
}
