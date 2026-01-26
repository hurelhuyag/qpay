/*
package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static feign.FeignException.errorStatus;
import static feign.Util.RETRY_AFTER;

public class JsonErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = errorStatus(methodKey, response, null, null);
        */
/*Date retryAfter = retryAfterDecoder.apply(firstOrNull(response.headers(), RETRY_AFTER));*//*

        */
/*if (retryAfter != null) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    retryAfter,
                    response.request());
        }*//*

        return exception;
    }
}
*/
