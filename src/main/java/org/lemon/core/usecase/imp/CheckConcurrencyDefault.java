package org.lemon.core.usecase.imp;

import org.lemon.core.usecase.interfaces.CheckConcurrency;
import org.lemon.exception.custom.ConcurrencyExceededException;
import org.lemon.repository.database.interfaces.GetConcurrencyByUserIdRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CheckConcurrencyDefault implements CheckConcurrency {

    private final GetConcurrencyByUserIdRepository getConcurrencyByUserIdRepository;

    @Inject
    public CheckConcurrencyDefault(GetConcurrencyByUserIdRepository getConcurrencyByUserIdRepository) {
        this.getConcurrencyByUserIdRepository = getConcurrencyByUserIdRepository;
    }

    @Override
    public void execute(Long userId) {
        Long concurrencyByUser = this.getConcurrencyByUserIdRepository.execute(userId);

        if(5 <= concurrencyByUser) {
            throw new ConcurrencyExceededException("Concurrency limit of 5 in the last 10 seconds was exceeded.");
        }
    }
}
