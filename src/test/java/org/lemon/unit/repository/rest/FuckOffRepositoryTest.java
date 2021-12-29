package org.lemon.unit.repository.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.core.entity.FuckOff;
import org.lemon.exception.custom.JSONException;
import org.lemon.exception.custom.RepositoryException;
import org.lemon.repository.rest.impl.FuckOffRestRepositoryRestDefault;
import org.lemon.repository.rest.interfaces.FuckOffRepository;
import org.lemon.unit.repository.rest.mock.MockedHttpClient;
import org.lemon.unit.repository.rest.mock.MockedHttpResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class FuckOffRepositoryTest {

    private FuckOffRepository instance;
    @Mock
    private MockedHttpClient mockedHttpClient;
    @Mock
    private MockedHttpResponse mockedHttpResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.instance = new FuckOffRestRepositoryRestDefault(new DefaultJsonJackson(), mockedHttpClient);
    }

    @Test
    public void whenServiceIsCalled_mustReturnAValidMessage() throws Exception {
        String expectedMessage = "Please choke on a bucket of cocks.";

        when(mockedHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockedHttpResponse);
        when(mockedHttpResponse.body()).thenReturn("{\n" +
                "    \"message\": \"Please choke on a bucket of cocks.\"\n" +
                "}");

        FuckOff response = this.instance.execute();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void whenServiceIsCalledButFormatIsNotExpected_mustFailToDeserialize() throws Exception {
        when(mockedHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockedHttpResponse);
        when(mockedHttpResponse.body()).thenReturn("prueba");

        Assertions.assertThrows(JSONException.class, () -> this.instance.execute());
    }

    @Test
    public void whenServiceIsCalledButThrowsError_mustThrowRepositoryException() throws Exception {
        when(mockedHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException());

        Assertions.assertThrows(RepositoryException.class, () -> this.instance.execute());
    }
}
