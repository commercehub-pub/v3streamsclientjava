package io.dsco.stream.shared;

import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface CommonStreamMethods
{
    default String getStreamPosition(StreamV3Api streamV3Api, String streamId, Logger logger)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("getting initial stream position");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.listStream(streamId);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for stream position");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for stream position: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when checking stream position: " + httpStatus);
        }

        //pull out the stream position from the response
//logger.info(future.get().getBody());
        return future.get().getBody().getArray().getJSONObject(0).getString("position");
    }

    default void updateStreamPosition(StreamV3Api streamV3Api, String streamId, String streamPosition, Logger logger)
    throws ExecutionException, InterruptedException
    {
        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.updateStreamPosition(streamId, streamPosition);

        int httpStatus = future.get().getStatus();
        if (httpStatus != 200) {
            throw new IllegalStateException(MessageFormat.format(
                    "unable to update stream {0} to position {1}. http response code: {2}: ",
                    streamId, streamPosition, httpStatus));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("stream position updated to: {0}", streamPosition));
        }
    }
}
