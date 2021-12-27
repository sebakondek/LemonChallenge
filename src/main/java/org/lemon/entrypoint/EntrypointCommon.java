package org.lemon.entrypoint;

import org.lemon.exception.custom.AuthorizationException;
import org.lemon.exception.custom.ValidationException;
import spark.Route;

import java.util.Objects;

public abstract class EntrypointCommon implements Route {

    protected static final String CALLER_ID_HEADER = "x-caller-id";

    protected int validateAndReturnCallerId(String callerId) {
        if(Objects.isNull(callerId)) {
            throw new ValidationException("Caller-id header cannot be null");
        }

        int userId = Integer.parseInt(callerId);

        if(1234 != userId) {
            throw new AuthorizationException("Caller-id is not authorized");
        }

        return userId;
    }
}
