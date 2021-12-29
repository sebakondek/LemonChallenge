package org.lemon.exception.custom;

import org.eclipse.jetty.http.HttpStatus;
import org.lemon.exception.entity.APIException;

public class LockException extends APIException {
    public LockException(String errorMessage) {
        super(HttpStatus.LOCKED_423, "already_locked", errorMessage);
    }
}
