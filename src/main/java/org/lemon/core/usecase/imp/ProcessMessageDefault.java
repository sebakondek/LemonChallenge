package org.lemon.core.usecase.imp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.lemon.core.entity.FuckOff;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import org.lemon.repository.interfaces.FuckOffRepository;

@Singleton
public class ProcessMessageDefault implements ProcessMessage {

    private FuckOffRepository fuckOffRepository;

    @Inject
    public ProcessMessageDefault(FuckOffRepository fuckOffRepository) {
        this.fuckOffRepository = fuckOffRepository;
    }

    @Override
    public FuckOff execute(int userId) {
        return this.fuckOffRepository.execute();
    }
}
