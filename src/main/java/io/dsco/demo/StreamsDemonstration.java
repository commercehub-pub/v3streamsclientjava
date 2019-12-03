package io.dsco.demo;

//TODO: make a sequence diagram of the fan out scenario

import io.dsco.demo.scenario.*;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.apiimpl.ApiBuilder;
import io.dsco.stream.command.supplier.UpdateInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.dsco.demo.Util.getConsoleInput;

public class StreamsDemonstration
{
    private static final Logger logger = LogManager.getLogger(StreamsDemonstration.class);

    //for demo purposes, limit how many item inventory objects get updated
    private static final int ITEM_INVENTORY_UPDATE_LIMITER = 5;

    private final StreamV3Api streamV3ApiRetailer;
    private final StreamV3Api streamV3ApiSupplier;
    private final InventoryV2Api inventoryV2ApiSupplier;
    private final InventoryV3Api inventoryV3ApiSupplier;
    private final InvoiceV3Api invoiceV3ApiSupplier;

    private final UpdateInventory updateInventoryCmd;

    private StreamsDemonstration()
    {
        //load the properties file to read configuration information
        Properties props = new Properties();
        try (InputStream is = StreamsDemonstration.class.getClassLoader().getResourceAsStream("dsco.properties")) {
            if (is == null) {
                //if this project was just checked out from source, there will be no properties file.
                // in that case, load the log4j2.xml file (which WILL exist) and use its directory location
                // to know where to save a default properties file.
                String resourcesPath = StreamsDemonstration.class.getClassLoader().getResource("log4j2.xml").getFile();
                resourcesPath = resourcesPath.substring(0, resourcesPath.lastIndexOf(File.separator + "out"));
                String outputPath = resourcesPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dsco.properties";
                //logger.info("saving file: " + outputPath);

                //create the properties, but with placeholder values that must be filled in
                props.setProperty("base.v2.url", "xxxxxx");
                props.setProperty("base.v3.url", "xxxxxx");
                props.setProperty("supplier.token", "xxxxxx");
                props.setProperty("retailer.token", "xxxxxx");

                try (OutputStream os = new FileOutputStream(outputPath)) {
                    props.store(os, null);
                }

                logger.info(
                        "No properties file found. A default properties file has been created in the resources directory.\n" +
                        "Please populate it with appropriate values before rerunning the application.");
                System.exit(1);
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
        invoiceV3ApiSupplier = ApiBuilder.getInvoiceV3Api(supplierToken, baseV3Url);

        updateInventoryCmd = new UpdateInventory(inventoryV2ApiSupplier, inventoryV3ApiSupplier);
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

    private void createOrderStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
    throws ExecutionException, InterruptedException
    {
        Map<String, Object> query = new HashMap<>();
        query.put("queryType", StreamV3Api.ObjectType.order.toString());
        //add other query filter criteria as needed

        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.order, query);
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

    private void doInventoryStreamProcessing()
            throws Throwable
    {
        String uniqueIdentifierKey = getConsoleInput("ItemInventory unique identifier (ex: sku, ean, gtin, isbn, mpn, upc, etc) > ");

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

        String selection = getConsoleInput(
                "Which scenario would you like to run?\n" +
                        "    1) Basic Inventory Stream\n" +
                        "    2) Fan-out Inventory Stream\n" +
                        "    3) Basic Error Recovery\n" +
                        "    4) Inventory Sync Stream Creation\n" +
                        "     > "
        );

        //TODO: new menu item: Simulate Activity In Streams - update iteminventory quantities available
        // with an ask of how many items to update

        switch (selection)
        {
            case "1":
                //updateInventoryCmd.execute(null);
                new InventoryBasic(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin();

                //TODO: set these to true when creating the stream
                //omitItemsOnHold
                //quantityChangeOnly
                //clearQuantityForStoppedItems

                break;

            case "2":
                updateInventoryCmd.execute(null);
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

    private void doOrderStreamProcessing()
    throws Exception
    {
        //create am order stream (retailer)
        String streamId = getConsoleInput("order streamId > ");

        //see if the stream has been created. if not, create it.
        if (!doesStreamExist(streamV3ApiRetailer, streamId)) {
            createOrderStream(streamV3ApiRetailer, streamId, "order stream");

            //it can sometimes take a bit of time before the stream becomes available; wait for it
            while (!doesStreamExist(streamV3ApiRetailer, streamId)) {
                logger.info("stream not yet created. waiting a bit and checking again...");
                Thread.sleep(500);
            }
        }

        new OrderBasic(streamId).begin();
    }

    private void begin()
    throws Throwable
    {
        //display the top level menu
        String selection = getConsoleInput(
            "1) Inventory Stream Processing\n" +
                    "2) Order Stream Processing\n" +
                    " > "
        );
        switch (selection)
        {
            case "1":
                doInventoryStreamProcessing();
                break;

            case "2":
                doOrderStreamProcessing();
                break;
        }
    }

    public static void main(String[] args)
    {
//        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
//        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");
        try {
            new StreamsDemonstration().begin();

        } catch (Throwable e) {
            logger.error("unexpected error", e);

        } finally {
            if (Unirest.isRunning()) Unirest.shutDown();
        }
    }

}
