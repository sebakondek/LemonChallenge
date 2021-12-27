package org.lemon.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lemon.configuration.module.Application;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.entrypoint.MessageEntrypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.Objects;
import java.util.TimeZone;

public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Initializing Main");

        TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        new Main().init();

        log.info("Successfully initialized Main");
    }

    @Override
    public void addRoutes() {
        Spark.get("/message", Application.getInstance(MessageEntrypoint.class), this::jsonTransformer);

        Spark.afterAfter("/*", (req, res) -> res.type("application/json"));
    }

    private String jsonTransformer(Object model) {
        try {
            return Objects.requireNonNull(Application.getInstance(DefaultJsonJackson.class))
                    .getObjectMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
