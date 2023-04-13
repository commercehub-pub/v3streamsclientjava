package io.dsco.stream.shared;

import io.dsco.demo.Util;
import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.domain.ItemInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetInventoryItems
{
    default List<ItemInventory> getInventoryItems(InventoryV3Api inventoryApi, String[] skus, Logger logger)
    throws Exception {
        List<ItemInventory> items = new ArrayList<>(skus.length);
        
        for (String sku : skus) {
            CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> 
                    inventoryApi.getInventoryObject(sku), 
                    inventoryApi, 
                    logger, 
                    "getInventoryItem", 
                    NetworkExecutor.HTTP_RESPONSE_200);
            
            JSONObject responseJson = future.get().getBody().getObject();
            String jsonStr = responseJson.toString();
            items.add(Util.gson().fromJson(jsonStr, ItemInventory.class));
        }
        
        return items;
    }
}
