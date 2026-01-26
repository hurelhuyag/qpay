package io.github.hurelhuyag.qpay;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@AutoConfiguration
@EnableFeignClients(clients = QpayApi.class)
@EnableConfigurationProperties(QpayProperties.class)
public class Config {

}
