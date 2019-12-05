package io.dsco.demo;

import io.dsco.demo.scenario.*;
import io.dsco.stream.api.*;
import io.dsco.stream.apiimpl.ApiBuilder;
import io.dsco.stream.command.supplier.UpdateInventory;
import io.dsco.stream.domain.InvoiceForUpdate;
import io.dsco.stream.domain.Order;
import io.dsco.stream.shared.NetworkExecutor;
import io.dsco.stream.shared.StreamCreator;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import static io.dsco.demo.Util.getConsoleInput;

public class StreamsDemonstration
implements StreamCreator
{
    private static final Logger logger = LogManager.getLogger(StreamsDemonstration.class);

    private final StreamV3Api streamV3ApiRetailer;
//    private final StreamV3Api streamV3ApiSupplier;
    private final InventoryV2Api inventoryV2ApiSupplier;
    private final InvoiceV3Api invoiceV3ApiSupplier;
    private final OrderV3Api orderV3ApiRetailer;
    private final OrderV3Api orderV3ApiSupplier;

//    private final String supplierAccountId;
    private final String retailerAccountId;

    private final UpdateInventory updateInventoryCmd;

    //objects creating during the app run
    private Order order;
    private InvoiceForUpdate invoice;

    private StreamsDemonstration()
    {
        //load the properties file to read configuration information
        Properties props = new Properties();
        try (InputStream is = StreamsDemonstration.class.getClassLoader().getResourceAsStream("dsco.properties")) {

            if (is == null) {
                //if this project was just checked out from source, there will be no properties file.
                // in that case, load the log4j2.xml file (which WILL exist) and use its directory location
                // to know where to save a default properties file.
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
//        streamV3ApiSupplier = ApiBuilder.getStreamV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        inventoryV2ApiSupplier = ApiBuilder.getInventoryV2Api(supplierV2Token, baseV2Url);
        InventoryV3Api inventoryV3ApiSupplier = ApiBuilder.getInventoryV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        invoiceV3ApiSupplier = ApiBuilder.getInvoiceV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        orderV3ApiRetailer = ApiBuilder.getOrderV3Api(retailerV3ClientId, retailerV3Secret, baseV3Url);
        orderV3ApiSupplier = ApiBuilder.getOrderV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);

//        supplierAccountId = props.getProperty("supplier.accountId");
        retailerAccountId = props.getProperty("retailer.accountId");

        updateInventoryCmd = new UpdateInventory(inventoryV2ApiSupplier, inventoryV3ApiSupplier);

        //let the network executor know where to go for auth token refreshes
        NetworkExecutor.getInstance().setAuthEndpoint(v3oAuthUrl);
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

    private void doCreateStream()
    throws Exception
    {
        String streamType = getConsoleInput(
        "\n1) ItemInventory Stream\n" +
                "2) Invoice Stream\n" +
                "3) Cancelled Stream\n" +
                "4) Undeliverable Shipment Stream\n" +
                "5) Shipped Stream\n" +
                " > "
        );

        String streamId = getConsoleInput("\nstreamId > ");

        Map<String, Object> query = new HashMap<>();
        switch (streamType)
        {
            case "1":
                query.put("queryType", StreamV3Api.ObjectType.inventory);
                query.put("omitItemsOnHold", true);
                query.put("quantityChangeOnly", true);
                break;

            case "2":
                query.put("queryType", StreamV3Api.ObjectType.invoice);
                break;

            case "3":
                query.put("queryType", StreamV3Api.ObjectType.orderitemchange);
                query.put("statuses", Collections.singletonList("cancelled"));
                break;

            case "4":
                query.put("queryType", StreamV3Api.ObjectType.undeliverableshipment);
                break;

            case "5":
                query.put("queryType", StreamV3Api.ObjectType.orderitemchange);
                query.put("statuses", Collections.singletonList("shipped"));
                break;
        }

        //see if the stream has been created. if not, create it.
        if (!doesStreamExist(streamId, streamV3ApiRetailer, logger)) {
            createStream(streamId, streamV3ApiRetailer, logger, query);

            //it can sometimes take a bit of time before the stream becomes available; wait for it
            while (!doesStreamExist(streamId, streamV3ApiRetailer, logger)) {
                logger.info("stream not yet created. waiting a bit and checking again...");
                Thread.sleep(500);
            }
        }

        begin();
    }

    private void doCauseActivityOnStream()
    throws Exception
    {
        //String streamId = getConsoleInput("\nstreamId > ");

        String streamType = getConsoleInput(
                "\n1) Update ItemInventory\n" +
                        "2) Create and acknowledge Order\n" +
                        "3) Create Invoice\n" +
                        "4) Cancel order line item\n" +
                        //"5) Add shipment\n" +
                        //"6) Mark shipment undeliverable\n" +
                        " > "
        );

        switch (streamType)
        {
            case "1": {
                int numItemsToUpdate = Integer.parseInt(getConsoleInput("\nnumber of items to update > "));
                updateInventoryCmd.execute(numItemsToUpdate);
            }
            break;

            case "2": {
                order = new OrderCreateAndAck(
                        retailerAccountId, inventoryV2ApiSupplier, orderV3ApiRetailer, orderV3ApiSupplier
                ).begin();
            }
            break;

            case "3": {
                if (order == null) {
                    System.out.println("\nyou must first create an order");
                } else {
                    invoice = new OrderInvoice(invoiceV3ApiSupplier).begin(order);
                }
            }
            break;

            case "4": {
                if (order == null || invoice == null) {
                    System.out.println("\nyou must first create an order and an invoice");
                } else {
                    new OrderCancelItem(orderV3ApiSupplier).begin(order, invoice);
                }
            }
            break;

//            case "5": {
//                if (order == null) {
//                    System.out.println("\nyou must first create an order");
//                } else {
//                    System.out.println("\nNOT YET IMPLEMENTED");
//                }
//            }
//            break;
//
//            case "6": {
//                if (order == null) {
//                    System.out.println("\nyou must first create an order");
//                } else {
//                    System.out.println("\nNOT YET IMPLEMENTED");
//                }
//            }
//            break;
        }

        begin();
    }

    private void doOtherStreamProcessing()
    throws Exception
    {
        String selection = getConsoleInput(
                "Type of stream?\n" +
                    "1) View Invoice Stream\n" +
                    "2) View Cancelled Stream\n" +
                    "3) View Undeliverable Shipment Stream\n" +
                    "4) View Shipped Stream\n" +
                    " > "
        );

        String streamId = getConsoleInput("\nstreamId > ");

        switch (selection)
        {
            case "1":
            case "2":
            case "3":
            case "4":
                new AnyStreamBasic(streamV3ApiRetailer, streamId).begin();

                break;
        }
    }

    private void begin()
    throws Exception
    {
        //display the top level menu
        String selection = getConsoleInput(
    "\n1) Create Stream\n" +
            "2) Cause activity on Stream\n" +
            "3) Inventory Stream Processing\n" +
            "4) Other Stream Processing\n" +
            " > "
        );
        switch (selection)
        {
            case "1":
                doCreateStream();
                break;

            case "2":
                doCauseActivityOnStream();
                break;

            case "3":
                doInventoryStreamProcessing();
                break;

            case "4":
                doOtherStreamProcessing();
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
