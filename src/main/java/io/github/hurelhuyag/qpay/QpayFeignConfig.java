package io.github.hurelhuyag.qpay;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class QpayFeignConfig {

    private final JsonMapper objectMapper = new JsonMapper();

    private TokenRes tokenRes;

    private final FeignClientProperties feignClientProperties;
    private final QpayProperties config;

    public QpayFeignConfig(FeignClientProperties feignClientProperties, QpayProperties config) {
        this.feignClientProperties = feignClientProperties;
        this.config = config;
    }

    /*@Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }*/

    private void obtainToken() {
        try {
            var url = new URL(feignClientProperties.getConfig().get("QpayApi").getUrl()+"/v2/auth/token");
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            try {
                conn.addRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder().encode((config.login()+":"+config.password()).getBytes())));
                tokenRes = objectMapper.readValue(conn.getInputStream(), TokenRes.class);
            } finally {
                conn.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshToken() {
        try {
            var url = new URL(feignClientProperties.getConfig().get("QpayApi").getUrl()+"/v2/auth/refresh");
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            try {
                conn.addRequestProperty("Authorization", "Bearer " + tokenRes.refreshToken());
                tokenRes = objectMapper.readValue(conn.getInputStream(), TokenRes.class);
            } finally {
                conn.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (tokenRes == null) {
                obtainToken();
            } else {
                if (tokenRes.isExpired()) {
                    refreshToken();
                }
            }
            requestTemplate.header("Authorization", "Bearer " + tokenRes.accessToken());
        };
    }

}