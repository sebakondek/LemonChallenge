package org.lemon.unit.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.core.entity.FuckOff;
import org.lemon.core.usecase.imp.ProcessMessageDefault;
import org.lemon.core.usecase.interfaces.CheckConcurrency;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import org.lemon.exception.custom.ConcurrencyExceededException;
import org.lemon.exception.custom.JSONException;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.interfaces.SaveConcurrencyRepository;
import org.lemon.repository.rest.interfaces.FuckOffRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

public class ProcessMessageTest {

    private ProcessMessage instance;
    @Mock
    private CheckConcurrency checkConcurrency;
    @Mock
    private FuckOffRepository fuckOffRepository;
    @Mock
    private SaveConcurrencyRepository saveConcurrencyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.instance = new ProcessMessageDefault(checkConcurrency, fuckOffRepository, saveConcurrencyRepository);
    }

    @Test
    public void whenNoErrors_mustReturnFuckOffResponse() {
        FuckOff expectedFuckOff = FuckOff.builder().message("prueba").build();

        when(fuckOffRepository.execute()).thenReturn(expectedFuckOff);

        AtomicReference<FuckOff> response = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> response.set(this.instance.execute(1234L)));
        Assertions.assertEquals(expectedFuckOff, response.get());
    }

    @Test
    public void whenCheckConcurrencyThrowsError_mustThrowSameError() {
        doThrow(ConcurrencyExceededException.class).when(checkConcurrency).execute(1234L);

        Assertions.assertThrows(ConcurrencyExceededException.class, () -> this.instance.execute(1234L));
        verifyNoInteractions(fuckOffRepository, saveConcurrencyRepository);
    }

    @Test
    public void whenFuckOffRepositoryThrowsError_mustThrowSameError() {
        doThrow(JSONException.class).when(fuckOffRepository).execute();

        Assertions.assertThrows(JSONException.class, () -> this.instance.execute(1234L));
        verify(checkConcurrency, times(1)).execute(1234L);
        verifyNoInteractions(saveConcurrencyRepository);
    }

    @Test
    public void whenSaveConcurrencyRepositoryThrowsError_mustThrowSameError() {
        doThrow(RepositoryException.class).when(saveConcurrencyRepository).execute(1234L);

        Assertions.assertThrows(RepositoryException.class, () -> this.instance.execute(1234L));
        verify(checkConcurrency, times(1)).execute(1234L);
        verify(fuckOffRepository, times(1)).execute();
    }
}
