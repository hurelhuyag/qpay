package io.github.hurelhuyag.qpay;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "QpayApi", primary = false, configuration = QpayFeignConfig.class)
public interface QpayApi {

    @PostMapping("/v2/invoice")
    InvoiceCreateRes createInvoice(@RequestBody InvoiceCreateReq req);

    @GetMapping("/v2/invoice/{id}")
    Invoice findInvoice(@PathVariable String id);

    @DeleteMapping("/v2/invoice/{id}")
    void deleteInvoice(@PathVariable String id);

    @PostMapping("/v2/payment/list")
    List<PaymentListRes> listPayments(@RequestBody PaymentListReq req);

    @GetMapping("/v2/payment/{id}")
    Object findPayments(@PathVariable String id);

    @PostMapping("/v2/payment/check")
    PaymentCheckRes checkPayments(@RequestBody PaymentCheckReq req);

    @DeleteMapping("/v2/payment/cancel/{id}")
    PaymentCancelRes cancelPayments(@PathVariable String id, @RequestBody PaymentCancelReq req);

    @DeleteMapping("/v2/payment/refund/{id}")
    PaymentCancelRes refundPayments(@PathVariable String id, @RequestBody PaymentCancelReq req);







}
