# QPay Payment Gateway API client

## References

- https://developer.qpay.mn

## Setup

```properties
spring.cloud.openfeign.client.config.QpayApi.url=https://merchant-sandbox.qpay.mn
qpay.login=TEST_MERCHANT
qpay.password=123456
```

## Create Invoice

```java
import io.github.hurelhuyag.qpay.InvoiceCreateReq;
import io.github.hurelhuyag.qpay.InvoiceReq;
import io.github.hurelhuyag.qpay.QpayApi;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.System.out;

@Service
class MyService {

    private final QpayApi qpayApi;

    MyService(QpayApi qpayApi) {
        this.qpayApi = qpayApi;
    }

    void createInvoice() {
        var res = qpayApi.createInvoice(
                new InvoiceCreateReq(
                        "TEST_INVOICE",
                        "1234567",
                        null,
                        "terminal",
                        null,
                        "order-1234567",
                        null,
                        false,
                        null,
                        false,
                        null,
                        BigDecimal.valueOf(100),
                        "https://portal.zeent.tech/api/v1/main/qpay/receive",
                        "order-1234567",
                        null
                )
        );
        out.println(res.invoiceId() + "\n" + res.qrImage());
    }

} 
```

