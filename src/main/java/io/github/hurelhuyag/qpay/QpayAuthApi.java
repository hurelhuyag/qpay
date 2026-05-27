package io.github.hurelhuyag.qpay;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "QpayAuthApi",
    url = "${spring.cloud.openfeign.client.config.QpayApi.url}",
    primary = false
)
public interface QpayAuthApi {

    @PostMapping("/v2/auth/token")
    TokenRes getToken(@RequestHeader("Authorization") String authorization);

    @PostMapping("/v2/auth/refresh")
    TokenRes refreshToken(@RequestHeader("Authorization") String authorization);

}
