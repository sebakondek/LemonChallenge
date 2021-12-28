package org.lemon.core.usecase.imp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.lemon.core.entity.FuckOff;
import org.lemon.core.usecase.interfaces.CheckConcurrency;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import org.lemon.repository.database.interfaces.SaveConcurrencyRepository;
import org.lemon.repository.rest.interfaces.FuckOffRepository;

@Singleton
public class ProcessMessageDefault implements ProcessMessage {

    private final CheckConcurrency checkConcurrency;
    private final FuckOffRepository fuckOffRepository;
    private final SaveConcurrencyRepository saveConcurrencyRepository;

    @Inject
    public ProcessMessageDefault(CheckConcurrency checkConcurrency,
                                 FuckOffRepository fuckOffRepository,
                                 SaveConcurrencyRepository saveConcurrencyRepository) {
        this.checkConcurrency = checkConcurrency;
        this.fuckOffRepository = fuckOffRepository;
        this.saveConcurrencyRepository = saveConcurrencyRepository;
    }

    @Override
    public FuckOff execute(Long userId) {
        this.checkConcurrency.execute(userId);

        FuckOff fuckOffResponse = this.fuckOffRepository.execute();

        this.saveConcurrencyRepository.execute(userId);

        return fuckOffResponse;
    }
}
