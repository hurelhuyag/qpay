package io.github.hurelhuyag.qpay;

/**
 * Standard error body returned by the QPay API on a non-2xx response (error_message).
 * <p>
 * Жишээ кодууд: VALIDATION_ERROR (400), UNAUTHORIZED_ERROR (401), FORBIDDEN_ERROR (403),
 * UNIQUE_ERROR (409), NOT_FOUND_ERROR (422), INTERNAL_ERROR (500).
 *
 * @param error алдааны код
 * @param message алдааны мессеж
 */
public record ErrorRes(String error, String message) {
}
