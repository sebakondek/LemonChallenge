package org.lemon.entrypoint;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.lemon.core.entity.FuckOff;
import org.lemon.core.usecase.interfaces.ProcessMessage;
import spark.Request;
import spark.Response;

@Singleton
public class MessageEntrypoint extends EntrypointCommon {

    private final ProcessMessage processMessage;

    @Inject
    public MessageEntrypoint(ProcessMessage processMessage) {
        this.processMessage = processMessage;
    }

    @Override
    public FuckOff handle(Request request, Response response) {
        Long userId = super.validateAndReturnCallerId(request.headers(CALLER_ID_HEADER));

        return this.processMessage.execute(userId);
    }
}
