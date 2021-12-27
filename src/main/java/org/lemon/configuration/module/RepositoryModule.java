package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import org.lemon.repository.impl.FuckOffRestRepositoryRestDefault;
import org.lemon.repository.interfaces.FuckOffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(RepositoryModule.class);

    @Override
    protected void configure() {
        log.info("Initializing Repositories");

        bind(FuckOffRepository.class).to(FuckOffRestRepositoryRestDefault.class);

        log.info("Successfully initialized Repositories");
    }
}
