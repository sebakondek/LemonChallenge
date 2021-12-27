package org.lemon.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Singleton;
import org.eclipse.jetty.http.HttpStatus;
import org.lemon.configuration.module.Application;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.exception.entity.APIException;
import org.lemon.exception.entity.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Objects;

import static spark.Spark.exception;
import static spark.Spark.notFound;

@Singleton
public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public void register() {
        exception(APIException.class, this::apiException);
        exception(Exception.class, this::genericError);

        notFound(this::handleNotFound);
    }

    protected void apiException(APIException e, Request request, Response response) {
        respond(response, new ErrorResponse(e.getStatusCode(), e.getErrorCode(), e));
    }

    protected void genericError(Exception e, Request request, Response response) {
        log.error("Responding with error", e);
        respond(response, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR_500, "runtime_error", e));
    }

    public Object handleNotFound(Request req, Response res) {
        return respond(res, new ErrorResponse(HttpStatus.NOT_FOUND_404, "not_found", req.url()));
    }

    protected String respond(Response response, ErrorResponse errorResponse) {
        response.status(errorResponse.getStatusCode());
        response.header("Content-Type", "application/json");

        String json;

        try {
            json = Objects.requireNonNull(Application.getInstance(DefaultJsonJackson.class))
                    .getObjectMapper().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error formatting json", e);
        }

        response.body(json);
        return json;
    }
}
