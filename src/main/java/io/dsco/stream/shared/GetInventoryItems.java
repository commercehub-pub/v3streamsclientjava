package io.dsco.stream.shared;

import com.google.gson.Gson;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.domain.ItemInventory;
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

public interface GetInventoryItems
{
    default List<ItemInventory> getInventoryItems(
            InventoryV2Api inventoryApi, @SuppressWarnings("SameParameterValue") String pageIdentifier,
            String updatedSince, Logger logger)
    throws ExecutionException, InterruptedException
    {
        List<ItemInventory> items;

        if (logger.isDebugEnabled()) {
            logger.debug("about to query for inventory items");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = inventoryApi.getAllInventory(pageIdentifier, updatedSince);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for inventory items");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for get items: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when retrieving items: " + httpStatus);
        }

        //don't bother with paging for the demo. just grab whatever came back in the first page.
        JSONObject responseJson = future.get().getBody().getObject();
        JSONArray itemInventoryJson = responseJson.getJSONArray("itemInventory");

        if (logger.isDebugEnabled()) {
            logger.debug("# of inventory items returned from query: " + itemInventoryJson.length());
        }

        //convert the items from json to java for easy manipulation
        items = new ArrayList<>(itemInventoryJson.length());
        for (int i=0; i<itemInventoryJson.length(); i++) {
            JSONObject itemJson = itemInventoryJson.getJSONObject(i);
            String jsonStr = itemJson.toString();
            items.add(new Gson().fromJson(jsonStr, ItemInventory.class));
        }
        return items;
    }
}
