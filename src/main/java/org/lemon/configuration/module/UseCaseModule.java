package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import org.lemon.core.usecase.imp.ProcessMessageDefault;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseCaseModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(UseCaseModule.class);

    @Override
    protected void configure() {
        log.info("Initializing UseCases");

        bind(ProcessMessage.class).to(ProcessMessageDefault.class);

        log.info("Successfully initialized UseCases");
    }
}
