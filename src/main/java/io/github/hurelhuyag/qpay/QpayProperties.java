package io.github.hurelhuyag.qpay;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qpay")
public record QpayProperties(String login, String password) {
}
