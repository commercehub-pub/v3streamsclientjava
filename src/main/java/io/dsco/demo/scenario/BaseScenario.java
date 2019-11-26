package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.ItemInventory;
import io.dsco.stream.domain.StreamItemInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

abstract class BaseScenario
{
    private final Logger logger;
    private final StreamV3Api streamV3Api;
    private final String streamId;
    private final String uniqueIdentifierKey;

    BaseScenario(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey, Logger logger)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
        this.uniqueIdentifierKey = uniqueIdentifierKey;
        this.logger = logger;
    }

    String createStreamSync()
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

    String getStreamPosition()
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

    List<StreamItemInventory> getStreamEventsFromPosition(String position)
    //throws ExecutionException, InterruptedException
    {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("getting events in stream {0} from position {1}", streamId, position));
            }

            CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.getStreamEventsFromPosition(streamId, position);

            return refactorParseStreamEvents(future);

        } catch (Exception e) {
            logger.error("unexpected error", e);
            return null;
        }
    }

    List<StreamItemInventory> getStreamEventsInRange(String startPosition, String endPosition)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "getting events in stream {0} from {1} to {2}",
                    streamId, startPosition, endPosition));
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.getStreamEventsInRange(streamId, startPosition, endPosition);

        return refactorParseStreamEvents(future);
    }


    private List<StreamItemInventory> refactorParseStreamEvents(CompletableFuture<HttpResponse<JsonNode>> future)
    throws ExecutionException, InterruptedException
    {
        List<StreamItemInventory> items;

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for stream events");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for stream events: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when retrieving stream events: " + httpStatus);
        }

        JSONArray list = future.get().getBody().getArray();

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
        }

        //in the real system, this would parse out each stream itemInventory, but for demo purposes all we care about is the
        // id and the sku, so that the stream position can be updated
        items = new ArrayList<>(list.length());
        for (int i=0; i<list.length(); i++) {
            JSONObject obj = list.getJSONObject(i);
            String id = obj.getString("id");
            StreamItemInventory.Source source = StreamItemInventory.Source.valueOf(obj.getString("source"));
            String sku = obj.getJSONObject("payload").getString(uniqueIdentifierKey); //for demo, we'll assume it's the sku, but it can be supplied as any of the valid json keys
            ItemInventory item = new ItemInventory();
            item.setSku(sku);
            items.add(new StreamItemInventory(id, source, item));
        }

        return items;
    }
}
