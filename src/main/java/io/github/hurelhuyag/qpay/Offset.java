package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Offset(
    @JsonProperty("page_number") int pageNumber,
    @JsonProperty("page_limit") int pageLimit
) {}