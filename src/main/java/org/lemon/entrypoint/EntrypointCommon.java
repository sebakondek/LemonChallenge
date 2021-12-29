package org.lemon.entrypoint;

import org.lemon.exception.custom.AuthorizationException;
import org.lemon.exception.custom.ValidationException;
import spark.Route;

import java.util.Objects;

public abstract class EntrypointCommon implements Route {

    protected static final String CALLER_ID_HEADER = "x-caller-id";

    protected Long validateAndReturnCallerId(String callerId) {
        if(Objects.isNull(callerId)) {
            throw new ValidationException("Caller-id header cannot be null");
        }

        long userId = Long.parseLong(callerId);

        if(1234 != userId) {
            throw new AuthorizationException("Caller-id is not authorized");
        }

        return userId;
    }
}
