package org.lemon.exception.entity;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ErrorResponse {

    private final Integer statusCode;
    private final String code;
    private final String message;
    private final String stacktrace;

    public ErrorResponse(Integer statusCode, String errorCode, String errorMessage) {
        this.statusCode = statusCode;
        this.message = errorMessage;
        this.code = errorCode;
        this.stacktrace = null;
    }

    public ErrorResponse(int statusCode, String errorCode, Throwable exception) {
        this.statusCode = statusCode;
        this.code = errorCode;
        this.message = exception.getMessage();
        this.stacktrace = ExceptionUtils.getStackTrace(exception);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStacktrace() {
        return stacktrace;
    }
}
