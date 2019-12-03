package io.dsco.stream.shared;

import io.dsco.stream.command.Command;
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

public abstract class StreamItemInventoryBase
implements Command<List<String>, List<StreamItemInventory>>
{
    private final String uniqueIdentifierKey;
    private final Logger logger;

    public StreamItemInventoryBase(String uniqueIdentifierKey, Logger logger)
    {
        this.uniqueIdentifierKey = uniqueIdentifierKey;
        this.logger = logger;
    }

    protected List<StreamItemInventory> refactorParseStreamEvents(CompletableFuture<HttpResponse<JsonNode>> future)
    throws ExecutionException, InterruptedException
    {
        JSONArray list = future.get().getBody().getArray();

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
        }

        //in the real system, this would parse out each stream itemInventory, but for demo purposes all we care about is the
        // id and the sku, so that the stream position can be updated
        List<StreamItemInventory> items = new ArrayList<>(list.length());
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
