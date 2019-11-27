package io.dsco.demo.scenario.base;

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

/**
 * a class rather than an interface because we can't have private methods in interfaces (until java 9).
 */
public class ItemInventoryMethods
{
    private final Logger logger;
    protected final StreamV3Api streamV3Api;
    protected final String streamId;
    private final String uniqueIdentifierKey;

    public ItemInventoryMethods(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey, Logger logger)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
        this.uniqueIdentifierKey = uniqueIdentifierKey;
        this.logger = logger;
    }

    //this can't throw an exception because it's used in a lambda function call
    public List<StreamItemInventory> getItemInventoryEventsFromPosition(String position)
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

    public List<StreamItemInventory> getItemInventoryEventsInRange(String startPosition, String endPosition)
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
