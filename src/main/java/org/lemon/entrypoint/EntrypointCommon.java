package org.lemon.entrypoint;

import com.google.common.collect.Sets;
import org.lemon.exception.custom.AuthorizationException;
import org.lemon.exception.custom.ValidationException;
import spark.Route;

import java.util.HashSet;
import java.util.Objects;

public abstract class EntrypointCommon implements Route {

    protected static final String CALLER_ID_HEADER = "x-caller-id";
    private static final HashSet<Long> AUTHORIZED_USERS = Sets.newHashSet(1234L,5678L);

    protected Long validateAndReturnCallerId(String callerId) {
        if(Objects.isNull(callerId)) {
            throw new ValidationException("Caller-id header cannot be null");
        }

        long userId = Long.parseLong(callerId);

        if(! AUTHORIZED_USERS.contains(userId)) {
            throw new AuthorizationException("Caller-id is not authorized");
        }

        return userId;
    }
}
