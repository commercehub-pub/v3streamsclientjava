package io.dsco.stream.shared;

import io.dsco.demo.Util;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.Stream;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public interface CommonStreamMethods
{
    default Stream getStreamPosition(StreamV3Api streamV3Api, String streamId, Integer partitionsId, Logger logger)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.listStreams(streamId, partitionsId);
        }, streamV3Api, logger, "getStreamPosition", NetworkExecutor.HTTP_RESPONSE_200);

        //since we're calling listStreams, there will be an array of streams, even though it's just one stream
        // coming back since we passed in a streamId
        JSONObject obj = future.get().getBody().getArray().getJSONObject(0);
//logger.info(future.get().getBody());
//logger.info(obj.toString());
        return Util.gson().fromJson(obj.toString(), Stream.class);
    }

    default void updateStreamPosition(StreamV3Api streamV3Api, String streamId, int partitionId, String streamPosition, Logger logger)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.updateStreamPosition(streamId, partitionId, streamPosition);
        }, streamV3Api, logger, "updateStreamPosition", NetworkExecutor.HTTP_RESPONSE_200);

        logger.debug(future.get().getBody());
    }
}
