package io.github.hurelhuyag.qpay;

import java.util.Base64;

public class PasswordGenerator {

    public static void main(String[] args) {
        var encoded = new String(Base64.getEncoder().encode("TEST_MERCHANT:123456".getBytes()));
        System.out.println(encoded);
    }
}
