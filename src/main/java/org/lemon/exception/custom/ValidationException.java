package org.lemon.exception.custom;

import org.eclipse.jetty.http.HttpStatus;
import org.lemon.exception.entity.APIException;

public class ValidationException extends APIException {

    public ValidationException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST_400, "validation_exception", errorMessage);
    }
}
