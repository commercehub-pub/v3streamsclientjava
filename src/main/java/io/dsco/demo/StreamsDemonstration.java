package io.dsco.demo;

import io.dsco.demo.scenario.*;
import io.dsco.stream.api.*;
import io.dsco.stream.apiimpl.ApiBuilder;
import io.dsco.stream.command.supplier.UpdateInventory;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static io.dsco.demo.Util.getConsoleInput;

public class StreamsDemonstration
{
    private static final Logger logger = LogManager.getLogger(StreamsDemonstration.class);

    private final StreamV3Api streamV3ApiRetailer;
    private final StreamV3Api streamV3ApiSupplier;
    private final InventoryV2Api inventoryV2ApiSupplier;
    private final InventoryV3Api inventoryV3ApiSupplier;
    private final InvoiceV3Api invoiceV3ApiSupplier;
    private final OrderV3Api orderV3ApiRetailer;
    private final OrderV3Api orderV3ApiSupplier;

    private final String supplierAccountId;
    private final String retailerAccountId;

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
                @SuppressWarnings("ConstantConditions")
                String currentWorkingDir = new File("x").getAbsolutePath();
                currentWorkingDir = currentWorkingDir.substring(0, currentWorkingDir.lastIndexOf(File.separator));
                String outputPath = currentWorkingDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dsco.properties";
                //logger.info("saving file: " + outputPath);

                //create the properties, but with placeholder values that must be filled in
                props.setProperty("base.v2.url", "xxxxxx");
                props.setProperty("base.v3.url", "xxxxxx");
                props.setProperty("v3.oauth.url", "xxxxxx");

                props.setProperty("retailer.v3.clientId", "xxxxxx");
                props.setProperty("retailer.v3.secret", "xxxxxx");
                props.setProperty("retailer.accountId", "xxxxxx");

                props.setProperty("supplier.v2.token", "xxxxxx");
                props.setProperty("supplier.v3.clientId", "xxxxxx");
                props.setProperty("supplier.v3.secret", "xxxxxx");
                props.setProperty("supplier.accountId", "xxxxxx");

                try (OutputStream os = new FileOutputStream(outputPath)) {
                    props.store(os, null);
                }

                logger.info(
                        "No properties file found. A default properties file has been created in the resources directory.\n" +
                        "Please populate it with appropriate values before rerunning the application.");
                System.exit(1);
            }
            props.load(is);

        } catch (Exception e) {
            logger.error("unable to load properties file", e);
            System.exit(1);
        }

        String baseV2Url = props.getProperty("base.v2.url");
        String baseV3Url = props.getProperty("base.v3.url");
        String v3oAuthUrl = props.getProperty("v3.oauth.url");

        String supplierV2Token = props.getProperty("supplier.v2.token");
        String supplierV3ClientId = props.getProperty("supplier.v3.clientId");
        String supplierV3Secret = props.getProperty("supplier.v3.secret");

        String retailerV3ClientId = props.getProperty("retailer.v3.clientId");
        String retailerV3Secret = props.getProperty("retailer.v3.secret");

        streamV3ApiRetailer = ApiBuilder.getStreamV3Api(retailerV3ClientId, retailerV3Secret, baseV3Url);
        streamV3ApiSupplier = ApiBuilder.getStreamV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        inventoryV2ApiSupplier = ApiBuilder.getInventoryV2Api(supplierV2Token, baseV2Url);
        inventoryV3ApiSupplier = ApiBuilder.getInventoryV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        invoiceV3ApiSupplier = ApiBuilder.getInvoiceV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        orderV3ApiRetailer = ApiBuilder.getOrderV3Api(retailerV3ClientId, retailerV3Secret, baseV3Url);
        orderV3ApiSupplier = ApiBuilder.getOrderV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);

        supplierAccountId = props.getProperty("supplier.accountId");
        retailerAccountId = props.getProperty("retailer.accountId");

        updateInventoryCmd = new UpdateInventory(inventoryV2ApiSupplier, inventoryV3ApiSupplier);

        //let the network executor know where to go for auth token refreshes
        NetworkExecutor.getInstance().setAuthEndpoint(v3oAuthUrl);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean doesStreamExist(StreamV3Api streamApi, String streamId)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamApi.listStream(streamId);
        }, streamApi, logger, "doesStreamExist", NetworkExecutor.HTTP_RESPONSE_200or404);

        return future.get().getStatus() == 200; //404=doesn't exist
    }

    private void createInventoryStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
    throws Exception
    {
        Map<String, Object> query = new HashMap<>();
        query.put("queryType", StreamV3Api.ObjectType.inventory.toString());
        query.put("omitItemsOnHold", true);
        query.put("quantityChangeOnly", true);
        query.put("clearQuantityForStoppedItems", true);
        //add other query filter criteria as needed

        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.inventory, query);
    }

    private void createInvoiceStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
    throws Exception
    {
        Map<String, Object> query = new HashMap<>();
        query.put("queryType", StreamV3Api.ObjectType.invoice.toString());
        //add other query filter criteria as needed

        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.invoice, query);
    }

//    private void createOrderStream(StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription)
//    throws Exception
//    {
//        Map<String, Object> query = new HashMap<>();
//        query.put("queryType", StreamV3Api.ObjectType.order.toString());
//        //add other query filter criteria as needed
//
//        createStreamRefactor(streamApi, streamId, streamDescription, StreamV3Api.ObjectType.order, query);
//    }

    private void createStreamRefactor(
            StreamV3Api streamApi, String streamId, @SuppressWarnings("SameParameterValue") String streamDescription,
            StreamV3Api.ObjectType objectType, Map<String, Object> query)
    throws Exception
    {
        /*CompletableFuture<HttpResponse<JsonNode>> future =*/ NetworkExecutor.getInstance().execute((x) -> {
            return streamApi.createStream(streamId, streamDescription, objectType, query);
        }, streamApi, logger, "createStream", NetworkExecutor.HTTP_RESPONSE_201);
    }

    private void doInventoryStreamProcessing()
    {
        String uniqueIdentifierKey = getConsoleInput("\nItemInventory unique identifier (ex: sku, ean, gtin, isbn, mpn, upc, etc) > ");

        String streamId = getConsoleInput("\nstreamId > ");

        String selection = getConsoleInput(
                "\nWhich scenario would you like to run?\n" +
                        "    1) Basic Inventory Stream\n" +
                        "    2) Fan-out Inventory Stream\n" +
                        "    3) Basic Error Recovery\n" +
                        "    4) Inventory Sync Stream Creation\n" +
                        "     > "
        );

        switch (selection)
        {
            case "1":
                new InventoryBasic(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin();
                break;

            case "2":
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
    {
        //String streamId = getConsoleInput("\nstreamId > ");

        new OrderBasic(
                inventoryV2ApiSupplier, orderV3ApiRetailer, orderV3ApiSupplier, invoiceV3ApiSupplier, retailerAccountId
        ).begin();
    }

    private void doCreateStream()
    throws Exception
    {
        String streamId = getConsoleInput("\nstreamId > ");

        String streamType = getConsoleInput(
        "\n1) ItemInventory Stream\n" +
                "2) Invoice Stream\n" +
                "3) Cancel Stream\n" +
                "4) Undeliverable Shipment Stream\n" +
                "5) Shipment Stream\n" +
                " > "
        );

        switch (streamType)
        {
            case "1": {
                //see if the stream has been created. if not, create it.
                if (!doesStreamExist(streamV3ApiRetailer, streamId)) {
                    createInventoryStream(streamV3ApiRetailer, streamId, "demonstration item inventory stream");

                    //it can sometimes take a bit of time before the stream becomes available; wait for it
                    while (!doesStreamExist(streamV3ApiRetailer, streamId)) {
                        logger.info("stream not yet created. waiting a bit and checking again...");
                        Thread.sleep(500);
                    }
                }
            }
            break;

            //TODO
//            case "2": {
//                //see if the stream has been created. if not, create it.
//                if (!doesStreamExist(streamV3ApiRetailer, streamId)) {
//                    createOrderStream(streamV3ApiRetailer, streamId, "order stream");
//
//                    //it can sometimes take a bit of time before the stream becomes available; wait for it
//                    while (!doesStreamExist(streamV3ApiRetailer, streamId)) {
//                        logger.info("stream not yet created. waiting a bit and checking again...");
//                        Thread.sleep(500);
//                    }
//                }
//            }
//            break;
        }

        begin();
    }

    private void doSimulateStreamActivity()
    throws Exception
    {
        //String streamId = getConsoleInput("\nstreamId > ");

        String streamType = getConsoleInput(
                "\n1) Update ItemInventory\n" +
                        "2) Create and acknowledge Order\n" +
                        "3) Create Invoice\n" +
                        "4) Cancel order line item\n" +
                        "5) Mark line item undeliverable\n" +
                        "6) Add shipment\n" +
                        " > "
        );

        switch (streamType)
        {
            case "1": {
                int numItemsToUpdate = Integer.parseInt(getConsoleInput("\nnumber of items to update > "));
                updateInventoryCmd.execute(numItemsToUpdate);
            }
            break;
        }

        begin();
    }

    private void begin()
    throws Exception
    {
        //display the top level menu
        String selection = getConsoleInput(
    "\n1) Create Stream\n" +
            "2) Cause activity on Stream\n" +
            "3) Inventory Stream Processing\n" +
            "4) Order Stream Processing\n" +
            " > "
        );
        switch (selection)
        {
            case "1":
                doCreateStream();
                break;

            case "2":
                doSimulateStreamActivity();
                break;

            case "3":
                doInventoryStreamProcessing();
                break;

            case "4":
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
