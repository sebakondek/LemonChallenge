package org.lemon.exception.custom;

import org.eclipse.jetty.http.HttpStatus;
import org.lemon.exception.entity.APIException;

public class ConcurrencyExceededException extends APIException {

    public ConcurrencyExceededException(String errorMessage) {
        super(HttpStatus.ENHANCE_YOUR_CALM_420, "enhance_your_calm", errorMessage);
    }
}
