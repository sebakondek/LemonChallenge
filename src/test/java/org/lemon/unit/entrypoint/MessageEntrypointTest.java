package org.lemon.unit.entrypoint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.configuration.util.LockManager;
import org.lemon.core.entity.FuckOff;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import org.lemon.entrypoint.MessageEntrypoint;
import org.lemon.exception.custom.AuthorizationException;
import org.lemon.exception.custom.LockException;
import org.lemon.exception.custom.ValidationException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class MessageEntrypointTest {

    protected static final String CALLER_ID_HEADER = "x-caller-id";

    private MessageEntrypoint instance;
    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private ProcessMessage processMessage;
    private LockManager lockManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.lockManager = new LockManager();

        this.instance = new MessageEntrypoint(processMessage, lockManager);
    }

    @Test
    public void whenHandleAndCallerIdIsPresent_mustReturnAFuckOffMessage() {
        FuckOff expectedFuckOff = FuckOff.builder().message("prueba").build();

        when(request.headers(CALLER_ID_HEADER)).thenReturn("1234");
        when(processMessage.execute(1234L)).thenReturn(expectedFuckOff);

        FuckOff resp = this.instance.handle(request, response);

        Assertions.assertEquals(expectedFuckOff, resp);
        Mockito.verify(processMessage, times(1)).execute(1234L);
    }

    @Test
    public void whenHandleAndCallerIdIsNotPresent_mustThrowValidationException() {
        when(request.headers(CALLER_ID_HEADER)).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.instance.handle(request, response));
        Mockito.verifyNoInteractions(processMessage);
    }

    @Test
    public void whenHandleAndCallerIdIsPresentButIsNotANumber_mustThrowNumberFormatException() {
        when(request.headers(CALLER_ID_HEADER)).thenReturn("abcd");

        Assertions.assertThrows(NumberFormatException.class, () -> this.instance.handle(request, response));
        Mockito.verifyNoInteractions(processMessage);
    }

    @Test
    public void whenHandleAndCallerIdIsPresentButIsNotAuthorized_mustThrowAuthorizationException() {
        when(request.headers(CALLER_ID_HEADER)).thenReturn("9675");

        Assertions.assertThrows(AuthorizationException.class, () -> this.instance.handle(request, response));
        Mockito.verifyNoInteractions(processMessage);
    }

    @Test
    public void whenHandleAndCallerIdIsPresentButIsBlocked_mustThrowLockException() {
        lockManager.addLockKey("1234");

        when(request.headers(CALLER_ID_HEADER)).thenReturn("1234");

        Assertions.assertThrows(LockException.class, () -> this.instance.handle(request, response));
        Mockito.verifyNoInteractions(processMessage);

        lockManager.clearLock();
    }
}
