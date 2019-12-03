package io.dsco.stream.shared;

import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public interface CommonStreamMethods
{
    default String getStreamPosition(StreamV3Api streamV3Api, String streamId, Logger logger)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.listStream(streamId);
        }, logger, "getStreamPosition", NetworkExecutor.HTTP_RESPONSE_200);

        //pull out the stream position from the response
        return future.get().getBody().getArray().getJSONObject(0).getString("position");
    }

    default void updateStreamPosition(StreamV3Api streamV3Api, String streamId, String streamPosition, Logger logger)
    throws Exception
    {
        /*CompletableFuture<HttpResponse<JsonNode>> future =*/ NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.updateStreamPosition(streamId, streamPosition);
        }, logger, "updateStreamPosition", NetworkExecutor.HTTP_RESPONSE_200);
    }
}
