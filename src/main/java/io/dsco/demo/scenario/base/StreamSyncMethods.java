package io.dsco.demo.scenario.base;

import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface StreamSyncMethods
{
    default String createStreamSync(StreamV3Api streamV3Api, String streamId, Logger logger)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("creating stream sync operation");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.createStreamOperation(streamId, StreamV3Api.OperationType.sync);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for sync operation");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for sync operation: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when creating sync operation: " + httpStatus);
        }

        //return the operationUuid
//logger.info(future.get().getBody());
        return future.get().getBody().getObject().getString("operationUuid");
    }
}
