package io.dsco.stream.shared;

import io.dsco.stream.command.Command;
import io.dsco.stream.domain.ItemInventory;
import io.dsco.stream.domain.StreamEventInventory;
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

public abstract class StreamItemInventoryBase
implements Command<List<String>, List<StreamEventInventory>>
{
    private final String uniqueIdentifierKey;
    private final Logger logger;

    public StreamItemInventoryBase(String uniqueIdentifierKey, Logger logger)
    {
        this.uniqueIdentifierKey = uniqueIdentifierKey;
        this.logger = logger;
    }

    protected List<StreamEventInventory> refactorParseStreamEvents(CompletableFuture<HttpResponse<JsonNode>> future)
    throws ExecutionException, InterruptedException
    {
        //in the real system, this would parse out each stream itemInventory, but for demo purposes all we care about is the
        // id and the unique identifier, so that the stream position can be updated

        /*
        the return structure is:

        {
            "ownerId": "the owner",
            "partitionId": 0,
            "events": [
                { ... }
            ]
        }

         */
logger.debug(future.get().getBody());
logger.debug("ownerId: " + future.get().getBody().getObject().getString("ownerId"));
logger.debug("partitionId: " + future.get().getBody().getObject().getInt("partitionId"));

        JSONArray list = future.get().getBody().getObject().getJSONArray("events");

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
        }

        List<StreamEventInventory> items = new ArrayList<>(list.length());
        for (int i=0; i<list.length(); i++) {
            JSONObject obj = list.getJSONObject(i);
            String id = obj.getString("id");
            StreamEventInventory.Source source = StreamEventInventory.Source.valueOf(obj.getString("source"));
            String sku = obj.getJSONObject("payload").getString(uniqueIdentifierKey); //for demo, we'll assume it's the sku, but it can be supplied as any of the valid json keys
            ItemInventory item = new ItemInventory();
            item.setSku(sku);
            items.add(new StreamEventInventory(id, source, item));
        }

        return items;
    }
}
