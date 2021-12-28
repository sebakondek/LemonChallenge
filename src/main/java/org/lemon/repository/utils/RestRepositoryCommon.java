package org.lemon.repository.utils;

import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.exception.custom.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class RestRepositoryCommon {

    private static final Logger log = LoggerFactory.getLogger(RestRepositoryCommon.class);
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    protected final DefaultJsonJackson json;

    public RestRepositoryCommon(DefaultJsonJackson json) {
        this.json = json;
    }

    protected HttpResponse<String> get(String uri) {
        try {
             HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("Error in get service for " + uri);
            throw new RepositoryException("Error in get service for " + uri, e);
        }
    }
}
