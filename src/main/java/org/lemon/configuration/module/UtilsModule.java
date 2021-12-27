package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(UtilsModule.class);

    @Override
    protected void configure() {
        log.info("Initializing Utils");

        bind(DefaultJsonJackson.class).toInstance(new DefaultJsonJackson());

        log.info("Successfully initialized Utils");
    }
}
