package io.dsco.stream.shared;

import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface StreamCreator
{
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    default boolean doesStreamExist(String streamId, StreamV3Api streamV3ApiRetailer, Logger logger)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3ApiRetailer.listStream(streamId);
        }, streamV3ApiRetailer, logger, "doesStreamExist", NetworkExecutor.HTTP_RESPONSE_200or404);

        return future.get().getStatus() == 200; //404=doesn't exist
    }

    default void createStream(String streamId, StreamV3Api streamV3ApiRetailer, Logger logger, Map<String, Object> query)
    throws Exception
    {
        StreamV3Api.ObjectType objectType = (StreamV3Api.ObjectType) query.get("queryType");

        /*CompletableFuture<HttpResponse<JsonNode>> future =*/ NetworkExecutor.getInstance().execute((x) -> {
            return streamV3ApiRetailer.createStream(streamId, "streamDescription", objectType, query);
        }, streamV3ApiRetailer, logger, "createStream", NetworkExecutor.HTTP_RESPONSE_201);
    }
}
