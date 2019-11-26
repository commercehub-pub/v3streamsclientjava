package io.dsco.demo;

//TODO: rename class to Demonstration
//TODO: remove references to Kohls in variables and method names
//TODO: make a sequence diagram of the fan out scenario

import com.google.gson.Gson;
import io.dsco.demo.scenario.InventoryBasic;
import io.dsco.demo.scenario.InventoryFanout;
import io.dsco.demo.scenario.InventoryErrorRecovery;
import io.dsco.demo.scenario.InventorySync;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.ItemInventory;
import io.dsco.stream.domain.ItemWarehouse;
import io.dsco.stream.impl.ApiBuilder;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StreamsDemonstration
{
    private static final Logger logger = LogManager.getLogger(StreamsDemonstration.class);

    //for demo purposes, limit how many item inventory objects get updated
    private static final int ITEM_INVENTORY_UPDATE_LIMITER = 5;

    private final StreamV3Api streamV3ApiRetailer;
    private final StreamV3Api streamV3ApiSupplier;
    private final InventoryV2Api inventoryV2ApiSupplier;
    private final InventoryV3Api inventoryV3ApiSupplier;

    private StreamsDemonstration()
    {
        //load the properties file to read configuration information
        Properties props = new Properties();
        try (InputStream is = StreamsDemonstration.class.getClassLoader().getResourceAsStream("dsco.properties")) {
            if (is == null) {
                throw new IllegalStateException("unable to find dsco.properties");
            }
            props.load(is);
        } catch (IOException e) {
            logger.error("unable to load properties file", e);
            System.exit(1);
        }

        String baseV2Url = props.getProperty("base.v2.url");
        String baseV3Url = props.getProperty("base.v3.url");
        String supplierToken = props.getProperty("supplier.token");
        String retailerToken = props.getProperty("retailer.token");

        streamV3ApiRetailer = ApiBuilder.getStreamV3Api(retailerToken, baseV3Url);
        streamV3ApiSupplier = ApiBuilder.getStreamV3Api(supplierToken, baseV3Url);
        inventoryV2ApiSupplier = ApiBuilder.getInventoryV2Api(supplierToken, baseV2Url);
        inventoryV3ApiSupplier = ApiBuilder.getInventoryV3Api(supplierToken, baseV3Url);
    }

    private boolean doesStreamExist(StreamV3Api streamApi, String streamId)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("checking for existence of stream: {0}", streamId));
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamApi.listStream(streamId);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("waiting for response for stream list: {0}", streamId));
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code stream: {0}: {1}", streamId, httpStatus));
        }

        if (httpStatus != 200 && httpStatus != 404) {
            throw new IllegalStateException("got invalid http response when listing streams: " + httpStatus);
        }

        return httpStatus == 200; //404 = doesn't exist
    }

    private void createInventoryStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
    throws ExecutionException, InterruptedException
    {
        Map<String, Object> query = new HashMap<>();
        query.put("queryType", StreamV3Api.ObjectType.inventory.toString());
        //add other query filter criteria as needed

        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.inventory, query);
    }

    private void createInvoiceStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
        throws ExecutionException, InterruptedException
    {
        Map<String, Object> query = new HashMap<>();
        query.put("queryType", StreamV3Api.ObjectType.invoice.toString());
        //add other query filter criteria as needed

        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.invoice, query);
    }

    private void createStreamRefactor(
            StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription,
            StreamV3Api.ObjectType objectType, Map<String, Object> query)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("creating stream: {0}", streamId));
        }

        CompletableFuture<HttpResponse<JsonNode>> future =
                streamApi.createStream(streamId, streamDescription, objectType, query);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("waiting for response for create stream: {0}", streamId));
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code stream: {0}: {1}", streamId, httpStatus));
        }
        if (httpStatus != 201) {
            throw new IllegalStateException("got invalid http response when creating stream: " + httpStatus);
        }
    }

    private List<ItemInventory> getInventoryItems(
            InventoryV2Api inventoryApi, @SuppressWarnings("SameParameterValue") String pageIdentifier, String updatedSince)
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

    private String updateInventorySmallBatch(InventoryV3Api inventoryApi, List<ItemInventory> items)
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("about to update {0} inventory items", items.size()));
        }

        CompletableFuture<HttpResponse<JsonNode>> future = inventoryApi.updateInventorySmallBatch(items);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for update");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for update items: {0}", httpStatus));
        }
        if (httpStatus != 202) {
            throw new IllegalStateException("got invalid http response when updating items: " + httpStatus);
        }

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
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("about to check status of batch update request");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = inventoryApi.getInventoryChangeLog(requestId);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for status check");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for status check: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when checking update status: " + httpStatus);
        }

        //in a real system you'd want to loop them and check the status of each item to make sure it was successful
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

    private void modifyDataToPopulateStream(String updatedSince)
    throws ExecutionException, InterruptedException
    {
        //grab a list of recently updated inventory items
        List<ItemInventory> itemInventoryList = getInventoryItems(inventoryV2ApiSupplier, null, updatedSince);

        if (logger.isDebugEnabled()) {
            logger.debug("updating warehouse quantities on items to cause items to populate into the stream...");
        }

        //for demo purposes, grab the quantity in the first warehouse for each item and increment by 1
        int count = 0;
        List<ItemInventory> changedItems = new ArrayList<>(ITEM_INVENTORY_UPDATE_LIMITER);
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
                if (count > ITEM_INVENTORY_UPDATE_LIMITER) break;

            } else {
                //remove from the list; it doesn't have a warehouse. safe to do since we're looping backwards
                itemInventoryList.remove(i);
            }
        }

        //have the supplier update the items (which will cause them to show up in the retailer stream)
        itemInventoryList = changedItems;
        String requestId = updateInventorySmallBatch(inventoryV3ApiSupplier, itemInventoryList);

        //check the result of the small batch; wait for it to complete
        //TODO: this could also be accomplished via reading from an update stream
        while (!isUpdateComplete(inventoryV3ApiSupplier, requestId)) {
            //some stuff is still pending; wait a bit and try again
            Thread.sleep(500);
        }
    }

    private void doInventoryStreamProcessing(String streamId, String updatedSince)
    throws Exception
    {
        String uniqueIdentifierKey = getConsoleInput("ItemInventory unique identifier (ex: sku, ean, gtin, isbn, mpn, upc, etc) > ");

        String selection = getConsoleInput(
                "Which scenario would you like to run?\n" +
                        "    1) Basic Inventory Stream\n" +
                        "    2) Fan-out Inventory Stream\n" +
                        "    3) Basic Error Recovery\n" +
                        "    4) Inventory Sync Stream Creation\n" +
                        "     > "
        );

        switch (selection)
        {
            case "1":
                modifyDataToPopulateStream(updatedSince);
                new InventoryBasic(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin();
                break;

            case "2":
                modifyDataToPopulateStream(updatedSince);
                int numberOfConsumers = Integer.parseInt(getConsoleInput("\nHow many concurrent consumers > "));
                new InventoryFanout(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin(numberOfConsumers);
                break;

            case "3":
                String startPosition = getConsoleInput("\nStream Start Position > ");
                String endPosition = getConsoleInput("Stream End Position > ");
                new InventoryErrorRecovery(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin(startPosition, endPosition);
                break;

            case "4":
                new InventorySync(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin();
                break;
        }
    }

    private void doOtherStreamProcessing(String streamId)
    throws Exception
    {
        String selection = getConsoleInput(
                "1) Invoice Stream\n" +
                        "2) Cancellation Stream\n" +
                        "3) Undeliverable Shipment Stream\n" +
                        "4) Shipment Stream\n" +
                        " > "
        );
        switch (selection)
        {
            case "1":
                //TODO: invoice stream
                createInvoiceStream(streamV3ApiSupplier, streamId, "supplier invoice stream");
                break;

            case "2":
                //TODO: cancellation stream
                //logger.warn("not yet implemented");
                //break;

            case "3":
                //TODO: undeliverable shipment stream
                //logger.warn("not yet implemented");
                //break;

            case "4":
                //TODO: shipment stream
                logger.warn("not yet implemented");
                break;
        }
    }

    private void begin()
    throws Exception
    {
        String streamId = getConsoleInput("streamId > ");

        //see if the stream has been created. if not, create it.
        if (!doesStreamExist(streamV3ApiRetailer, streamId)) {
            createInventoryStream(streamV3ApiRetailer, streamId, "demonstration stream");

            //it can sometimes take a bit of time before the stream becomes available; wait for it
            while (!doesStreamExist(streamV3ApiRetailer, streamId)) {
                logger.info("stream not yet created. waiting a bit and checking again...");
                Thread.sleep(500);
            }
        }

        //display the top level menu
        String selection = getConsoleInput(
            "1) Inventory Stream Processing\n" +
                    "2) Other Stream Processing\n" +
                    "3) Order Creation\n" +
                    " > "
        );
        switch (selection)
        {
            case "1":
                //arbitrary date to limit stream to a smaller subset for demo purposes
                String updatedSince = "2019-08-15T18:26:00Z";
                doInventoryStreamProcessing(streamId, updatedSince);
                break;

            case "2":
                doOtherStreamProcessing(streamId);
                break;

            case "3":
                //TODO: add in order creation
                break;
        }
    }

    public static void main(String[] args)
    {
//        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
//        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");
        try {
            new StreamsDemonstration().begin();

        } catch (Exception e) {
            logger.error("unexpected error", e);

        } finally {
            if (Unirest.isRunning()) Unirest.shutDown();
        }
    }

    //utility method to read console input
    private static @NotNull String getConsoleInput(@NotNull String prompt)
    {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            return "";
        }
    }
}

/*
 TODO: things i haven't been able to get working

 i can't find any doc on getting Unirest to show low level http request/response traffic. all i could find was a post saying
 that under the covers it uses apache http client, and so setting up logging for that should work. but ... i haven't been
 able to get that working. another pair of eyes might be nice, just in case we want to show that level of detail for some reason.

 an alternative would be to get some type of traffic logger going (what's the one you use?)
 */