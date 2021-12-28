package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import org.lemon.repository.database.impl.GetConcurrencyByUserIdDBRepository;
import org.lemon.repository.database.impl.SaveConcurrencyDBRepository;
import org.lemon.repository.database.interfaces.GetConcurrencyByUserIdRepository;
import org.lemon.repository.database.interfaces.SaveConcurrencyRepository;
import org.lemon.repository.rest.impl.FuckOffRestRepositoryRestDefault;
import org.lemon.repository.rest.interfaces.FuckOffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(RepositoryModule.class);

    @Override
    protected void configure() {
        log.info("Initializing Repositories");

        bind(GetConcurrencyByUserIdRepository.class).to(GetConcurrencyByUserIdDBRepository.class);
        bind(FuckOffRepository.class).to(FuckOffRestRepositoryRestDefault.class);
        bind(SaveConcurrencyRepository.class).to(SaveConcurrencyDBRepository.class);

        log.info("Successfully initialized Repositories");
    }
}
