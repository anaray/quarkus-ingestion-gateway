package com.github.anaray.gateway;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Path("/events")
@Retry(maxRetries = 2)
@Tag(name = "Ingestion Gateway for events", description = "The REST API endpoint for ingesting events")
public class EventsGatewayResource {

    private static final Logger LOGGER = Logger.getLogger("EventsGatewayResource");
    private final Map success = Collections.singletonMap("response", "success");
    @Inject
    @Channel("events")
    Emitter<byte[]> eventsEmitter;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "{response : success}")
    @APIResponse(responseCode = "500", description = "{response : failure}")
    @Timeout(250)
    @Retry(maxRetries = 5)
    public CompletionStage<Response> enqueuePayload(byte[] payload) {
        LOGGER.infof("Sending http body payload of size %s",
                payload.length
        );

        return eventsEmitter.send(payload).thenApply(response -> Response.ok(success).build()).exceptionally(throwable -> Response.serverError().build());
    }

}