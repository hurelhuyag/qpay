package io.github.hurelhuyag.qpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(QpayApi qpayApi) {
        return  args -> {
            log.error("createInvoice");
            var invoice = qpayApi.createInvoice(new InvoiceCreateReq(
                    "TEST_INVOICE",
                    "1234567",
                    null,
                    "terminal",
                    null,
                    "test",
                    null,
                    false,
                    null,
                    false,
                    null,
                    BigDecimal.valueOf(100),
                    "https://bd5492c3ee85.ngrok.io/payments?payment_id=1234567",
                    null,
                    null
            ));
            log.error("invoiceId: {}", invoice.invoiceId());
            log.error("QR Text: {}", invoice.qrText());
            log.error("QR Code: {}", invoice.qrImage());

            var invoice2 = qpayApi.findInvoice(invoice.invoiceId());

            log.error("invoiceId: {}", invoice2.id());

            qpayApi.deleteInvoice(invoice2.id());

        };
    }
}
