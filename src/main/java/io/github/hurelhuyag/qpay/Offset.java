package io.github.hurelhuyag.qpay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Хуудаслалт. Used as {@code offset} in the payment list/check requests.
 *
 * @param pageNumber page_number, хуудасны тоо (min 1, max 100)
 * @param pageLimit page_limit, хуудасны хязгаар (min 1, max 100)
 */
public record Offset(
    @JsonProperty("page_number") int pageNumber,
    @JsonProperty("page_limit") int pageLimit
) {}