package org.lemon.unit.entrypoint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.entrypoint.HealthCheckEntrypoint;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

public class HealthCheckEntrypointTest {

    private HealthCheckEntrypoint instance;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.instance = new HealthCheckEntrypoint();
    }

    @Test
    public void whenHandle_mustReturnPong() {
        String resp = this.instance.handle(request, response);

        Assertions.assertEquals("pong", resp);
    }
}
