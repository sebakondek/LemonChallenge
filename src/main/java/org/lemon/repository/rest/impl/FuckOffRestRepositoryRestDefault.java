package org.lemon.repository.rest.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.lemon.configuration.util.DefaultJsonJackson;
import org.lemon.core.entity.FuckOff;
import org.lemon.exception.custom.JSONException;
import org.lemon.repository.rest.interfaces.FuckOffRepository;
import org.lemon.repository.utils.FuckOffURIs;
import org.lemon.repository.utils.RestRepositoryCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

@Singleton
public class FuckOffRestRepositoryRestDefault extends RestRepositoryCommon implements FuckOffRepository {

    private static final Logger log = LoggerFactory.getLogger(FuckOffRestRepositoryRestDefault.class);
    private static final String BASE_URI = "https://foaas.com";

    @Inject
    public FuckOffRestRepositoryRestDefault(DefaultJsonJackson json) {
        super(json);
    }

    @Override
    public FuckOff execute() {
        HttpResponse<String> response = super.get(BASE_URI + FuckOffURIs.getRandomURI());

        try {
            return super.json.getObjectMapper().readValue(response.body(), FuckOff.class);
        } catch (Exception e) {
            log.error("Error deserealizing FuckOff class.");
            throw new JSONException("Error deserealizing FuckOff class.", e);
        }
    }
}
