package org.lemon.unit.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.core.usecase.imp.CheckConcurrencyDefault;
import org.lemon.core.usecase.interfaces.CheckConcurrency;
import org.lemon.exception.custom.ConcurrencyExceededException;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.database.interfaces.GetConcurrencyByUserIdRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CheckConcurrencyTest {

    private static final int CONCURRENCY_TIME_WINDOW = 10;

    private CheckConcurrency instance;
    @Mock
    private GetConcurrencyByUserIdRepository getConcurrencyByUserIdRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.instance = new CheckConcurrencyDefault(getConcurrencyByUserIdRepository);
    }

    @Test
    public void whenConcurrencyByUserIsLowerThan5_mustNotThrowException() {
        when(getConcurrencyByUserIdRepository.execute(1234L, CONCURRENCY_TIME_WINDOW)).thenReturn(4L);

        Assertions.assertDoesNotThrow(() -> this.instance.execute(1234L));
        verify(getConcurrencyByUserIdRepository, times(1)).execute(1234L, CONCURRENCY_TIME_WINDOW);
    }

    @Test
    public void whenConcurrencyByUserIsEqualTo5_mustThrowException() {
        when(getConcurrencyByUserIdRepository.execute(1234L, CONCURRENCY_TIME_WINDOW)).thenReturn(5L);

        Assertions.assertThrows(ConcurrencyExceededException.class, () -> this.instance.execute(1234L));
        verify(getConcurrencyByUserIdRepository, times(1)).execute(1234L, CONCURRENCY_TIME_WINDOW);
    }

    @Test
    public void whenConcurrencyByUserIsGreaterThan5_mustNotThrowException() {
        when(getConcurrencyByUserIdRepository.execute(1234L, CONCURRENCY_TIME_WINDOW)).thenReturn(6L);

        Assertions.assertThrows(ConcurrencyExceededException.class, () -> this.instance.execute(1234L));
        verify(getConcurrencyByUserIdRepository, times(1)).execute(1234L, CONCURRENCY_TIME_WINDOW);
    }

    @Test
    public void whenConcurrencyByUserThrowsException_mustThrowSameException() {
        when(getConcurrencyByUserIdRepository.execute(1234L, CONCURRENCY_TIME_WINDOW)).thenThrow(RepositoryException.class);

        Assertions.assertThrows(RepositoryException.class, () -> this.instance.execute(1234L));
        verify(getConcurrencyByUserIdRepository, times(1)).execute(1234L, CONCURRENCY_TIME_WINDOW);
    }
}
