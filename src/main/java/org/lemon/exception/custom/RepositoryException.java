package org.lemon.exception.custom;

import org.eclipse.jetty.http.HttpStatus;
import org.lemon.exception.entity.APIException;

public class RepositoryException extends APIException {

    public RepositoryException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR_500, "rest_exception");
    }
}
