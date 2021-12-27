package org.lemon.entrypoint;

import com.google.inject.Singleton;
import spark.Request;
import spark.Response;
import spark.Route;

@Singleton
public class HealthCheckEntrypoint implements Route {

    @Override
    public Object handle(Request request, Response response) {
        response.header("Content-Type", "text/plain");
        return "pong";
    }
}