package ru.alexz.tinkofftesttask.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_PARAMETER("Parameter [${parameterName}] value is invalid", HttpStatus.BAD_REQUEST),
    INVALID_BODY("Invalid request body", HttpStatus.BAD_REQUEST),
    INVALID_URI("Invalid URI", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CONTENT_TYPE("Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    INVALID_CREATED_DATE("Parameter [DT_CREATED] should not be from future", HttpStatus.BAD_REQUEST),
    APPLICATIONS_NOT_FOUND("Applications for contactId are not found", HttpStatus.NOT_FOUND),
    CONTACT_NOT_FOUND("Contact is not found for contactId", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    public static final String PARAMETER_NAME = "parameterName";

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
