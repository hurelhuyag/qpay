/*
package io.github.hurelhuyag.qpay;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "QpayAuthApi", primary = false)
public interface QpayAuthApi {

    @PostMapping("/v2/auth/token")
    TokenRes getToken(@RequestHeader("Authorization") String authorization);

    @PostMapping("/v2/auth/refresh")
    TokenRes refreshToken(@RequestHeader("Authorization") String authorization);

}
*/
