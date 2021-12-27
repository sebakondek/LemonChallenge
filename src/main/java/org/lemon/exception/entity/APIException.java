package org.lemon.exception.entity;

public class APIException extends RuntimeException {

    private final Integer statusCode;
    private final String errorCode;

    public APIException(Integer statusCode, String errorCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public APIException(String message, Throwable cause, Integer statusCode, String errorCode) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public APIException(Throwable cause, Integer statusCode, String errorCode) {
        super(cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
