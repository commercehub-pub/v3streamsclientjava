package io.dsco.stream.shared;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.domain.ItemInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetInventoryItems
{
    default List<ItemInventory> getInventoryItems(
            InventoryV2Api inventoryApi, @SuppressWarnings("SameParameterValue") String pageIdentifier,
            String updatedSince, Logger logger)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return inventoryApi.getAllInventory(pageIdentifier, updatedSince);
        }, inventoryApi, logger, "getInventoryItems", NetworkExecutor.HTTP_RESPONSE_200);

        //don't bother with paging for the demo. just grab whatever came back in the first page.
        JSONObject responseJson = future.get().getBody().getObject();
        JSONArray itemInventoryJson = responseJson.getJSONArray("itemInventory");

        if (logger.isDebugEnabled()) {
            logger.debug("# of inventory items returned from query: " + itemInventoryJson.length());
        }

        //convert the items from json to java for easy manipulation
        List<ItemInventory> items = new ArrayList<>(itemInventoryJson.length());
        for (int i=0; i<itemInventoryJson.length(); i++) {
            JSONObject itemJson = itemInventoryJson.getJSONObject(i);
            String jsonStr = itemJson.toString();
            items.add(Util.gson().fromJson(jsonStr, ItemInventory.class));
        }
        return items;
    }
}
