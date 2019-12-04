package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.ItemInventory;
import io.dsco.stream.domain.ItemWarehouse;
import io.dsco.stream.shared.GetInventoryItems;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UpdateInventory
implements Command<Integer, Void>, GetInventoryItems
{
    private static final Logger logger = LogManager.getLogger(UpdateInventory.class);

    private final InventoryV2Api inventoryV2Api;
    private final InventoryV3Api inventoryV3Api;

    public UpdateInventory(InventoryV2Api inventoryV2Api, InventoryV3Api inventoryV3Api)
    {
        this.inventoryV2Api = inventoryV2Api;
        this.inventoryV3Api = inventoryV3Api;
    }

    @Override
    public Void execute(Integer numberItemsToUpdate) throws Exception
    {
        if (numberItemsToUpdate == null) {
            numberItemsToUpdate = 5;
        }

        //TODO: don't hardcode
        //arbitrary date to limit stream to a smaller subset for demo purposes
        String updatedSince = "2019-08-15T18:26:00Z";

        //grab a list of recently updated inventory items
        List<ItemInventory> itemInventoryList = getInventoryItems(inventoryV2Api, null, updatedSince, logger);

        if (logger.isDebugEnabled()) {
            logger.debug("updating warehouse quantities on items to cause items to populate into the stream...");
        }

        //for demo purposes, grab the quantity in the first warehouse for each item and increment by 1
        int count = 0;
        List<ItemInventory> changedItems = new ArrayList<>(numberItemsToUpdate);
        for (int i = itemInventoryList.size() - 1; i >= 0; i--) {
            ItemInventory itemInventory = itemInventoryList.get(i);

            //even though spec says all items must have a warehouse, occasionally there is one without; ignore those
            if (itemInventory.getWarehouses() != null && itemInventory.getWarehouses().size() > 0) {
                ItemWarehouse itemWarehouse = itemInventory.getWarehouses().get(0);
                int newQuantity = itemWarehouse.getQuantity() == null ? 1 : itemWarehouse.getQuantity() + 1;
                itemWarehouse.setQuantity(newQuantity);

                //also increase the quantity available so the warehouse doesn't have more than are available to have
                itemInventory.setQuantityAvailable(itemInventory.getQuantityAvailable() + 1);
                changedItems.add(itemInventory);

                //break out early based on the limit set for demo purposes
                count++;
                if (count >= numberItemsToUpdate) break;

            } else {
                //remove from the list; it doesn't have a warehouse. safe to do since we're looping backwards
                itemInventoryList.remove(i);
            }
        }

        //have the supplier update the items (which will cause them to show up in the retailer stream)
        itemInventoryList = changedItems;
        String requestId = updateInventorySmallBatch(inventoryV3Api, itemInventoryList);

        //check the result of the small batch; wait for it to complete
        //TODO: this could also be accomplished via reading from an update stream
        while (!isUpdateComplete(inventoryV3Api, requestId)) {
            //some stuff is still pending; wait a bit and try again
            Thread.sleep(500);
        }

        return null;
    }

    private String updateInventorySmallBatch(InventoryV3Api inventoryApi, List<ItemInventory> items)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {

//logger.info(MessageFormat.format("\n{0}\n", new Gson().toJson(items)));


            return inventoryApi.updateInventorySmallBatch(items);
        }, inventoryApi, logger, "updateInventorySmallBatch", NetworkExecutor.HTTP_RESPONSE_202);

        //since this is an asynchronous api, grab the "requestId" from the response. it will be needed to check
        // the result of the operation at a later time.
        JSONObject responseJson = future.get().getBody().getObject();
        String requestId = responseJson.getString("requestId");

        if (logger.isDebugEnabled()) {
            logger.debug("batch update async call requestId: " + requestId);
        }

        return requestId;
    }

    private boolean isUpdateComplete(InventoryV3Api inventoryApi, String requestId)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return inventoryApi.getInventoryChangeLog(requestId);
        }, inventoryApi, logger, "getInventoryChangeLog", NetworkExecutor.HTTP_RESPONSE_200);

        //see if each item is successful, pending, or failed
        JSONArray jsonList = future.get().getBody().getObject().getJSONArray("logs");

        int numSuccess = 0;
        int numPending = 0;
        int numFails = 0;
        for (int i=0; i<jsonList.length(); i++) {
            JSONObject logItem = jsonList.getJSONObject(i);
            String status = logItem.getString("status");
            switch (status)
            {
                case "success": numSuccess++; break;
                case "pending": numPending++; break;
                default: numFails++;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("total items updated: {0}, success: {1}, pending: {2}, fails: {3}", jsonList.length(), numSuccess, numPending, numFails));
        }

        if (numFails > 0) {
            throw new IllegalStateException("failed on item updates");
        }

        return numPending == 0;
    }
}
