package org.lemon.exception.custom;

import com.google.inject.Singleton;
import org.eclipse.jetty.http.HttpStatus;
import org.lemon.exception.entity.APIException;

@Singleton
public class AuthorizationException extends APIException {

    public AuthorizationException(String errorMessage) {
        super(HttpStatus.UNAUTHORIZED_401, "not_authorized", errorMessage);
    }
}
