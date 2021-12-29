package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.configuration.util.LockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;

public class UtilsModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(UtilsModule.class);

    @Override
    protected void configure() {
        log.info("Initializing Utils");

        bind(DefaultJsonJackson.class).toInstance(new DefaultJsonJackson());
        bind(HttpClient.class).toInstance(HttpClient.newHttpClient());
        bind(LockManager.class).toInstance(new LockManager());

        log.info("Successfully initialized Utils");
    }
}
