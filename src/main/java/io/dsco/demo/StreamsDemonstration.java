package io.dsco.demo;

import com.google.gson.Gson;
import io.dsco.demo.scenario.*;
import io.dsco.stream.api.*;
import io.dsco.stream.apiimpl.ApiBuilder;
import io.dsco.stream.command.retailer.CreateStreamOperation;
import io.dsco.stream.command.retailer.GetAnyEventsFromPosition;
import io.dsco.stream.command.retailer.UpdateStreamPartitionSize;
import io.dsco.stream.command.supplier.UpdateInventory;
import io.dsco.stream.domain.Invoice;
import io.dsco.stream.domain.Order;
import io.dsco.stream.shared.NetworkExecutor;
import io.dsco.stream.shared.StreamCreator;
import kong.unirest.GenericType;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.dsco.demo.Util.getConsoleInput;

public class StreamsDemonstration
implements StreamCreator
{
    private static final Logger logger = LogManager.getLogger(StreamsDemonstration.class);

    private final StreamV3Api streamV3ApiRetailer;
//    private final StreamV3Api streamV3ApiSupplier;
    private final InvoiceV3Api invoiceV3ApiSupplier;
    private final OrderV3Api orderV3ApiRetailer;
    private final InventoryV3Api inventoryV3ApiSupplier;
    private final OrderV3Api orderV3ApiSupplier;
    private final String[] skus;

//    private final String supplierAccountId;
    private final String retailerAccountId;

    private final UpdateInventory updateInventoryCmd;

    //objects creating during the app run
    private Order order;
    private Invoice invoice;

    private StreamsDemonstration()
    {
        //load the properties file to read configuration information
        Properties props = new Properties();
        try (InputStream is = StreamsDemonstration.class.getClassLoader().getResourceAsStream("dsco.properties")) {

            if (is == null) {
                //if this project was just checked out from source, there will be no properties file.
                // in that case, use the current working directory to know where to save a default properties file.
                String currentWorkingDir = new File("x").getAbsolutePath();
                currentWorkingDir = currentWorkingDir.substring(0, currentWorkingDir.lastIndexOf(File.separator));
                String outputPath = currentWorkingDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dsco.properties";
                //logger.info("saving file: " + outputPath);

                //create the properties, but with placeholder values that must be filled in
                props.setProperty("base.v3.url", "xxxxxx");
                props.setProperty("v3.oauth.url", "xxxxxx");

                props.setProperty("retailer.v3.clientId", "xxxxxx");
                props.setProperty("retailer.v3.secret", "xxxxxx");
                props.setProperty("retailer.accountId", "xxxxxx");

                props.setProperty("supplier.v3.clientId", "xxxxxx");
                props.setProperty("supplier.v3.secret", "xxxxxx");
                props.setProperty("supplier.accountId", "xxxxxx");
                props.setProperty("supplier.skus", "SKU1,SKU2,SKU3,...");

                try (OutputStream os = Files.newOutputStream(Paths.get(outputPath))) {
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

        String baseV3Url = props.getProperty("base.v3.url");
        String v3oAuthUrl = props.getProperty("v3.oauth.url");

        String supplierV3ClientId = props.getProperty("supplier.v3.clientId");
        String supplierV3Secret = props.getProperty("supplier.v3.secret");

        String retailerV3ClientId = props.getProperty("retailer.v3.clientId");
        String retailerV3Secret = props.getProperty("retailer.v3.secret");
        
        skus = props.getProperty("supplier.skus").split(",");

        streamV3ApiRetailer = ApiBuilder.getStreamV3Api(retailerV3ClientId, retailerV3Secret, baseV3Url);
//        streamV3ApiSupplier = ApiBuilder.getStreamV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        invoiceV3ApiSupplier = ApiBuilder.getInvoiceV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        orderV3ApiRetailer = ApiBuilder.getOrderV3Api(retailerV3ClientId, retailerV3Secret, baseV3Url);
        orderV3ApiSupplier = ApiBuilder.getOrderV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);
        inventoryV3ApiSupplier = ApiBuilder.getInventoryV3Api(supplierV3ClientId, supplierV3Secret, baseV3Url);

//        supplierAccountId = props.getProperty("supplier.accountId");
        retailerAccountId = props.getProperty("retailer.accountId");

        updateInventoryCmd = new UpdateInventory(inventoryV3ApiSupplier, skus);

        //let the network executor know where to go for auth token refreshes
        NetworkExecutor.getInstance().setAuthEndpoint(v3oAuthUrl);
    }

    private void doInventoryStreamProcessing()
    {
        String uniqueIdentifierKey = "sku"; //getConsoleInput("\nItemInventory unique identifier to display (ex: sku, ean, gtin, isbn, mpn, upc, etc) > ");

        String selection = getConsoleInput(
                "\nWhich scenario would you like to run?\n" +
                        "    1) Basic Inventory Stream\n" +
                        "    2) Fan-out Inventory Stream\n" +
                        "    3) Basic Error Recovery\n" +
                        "     > "
        );

        String streamId = getConsoleInput("\nstreamId > ");

        switch (selection)
        {
            case "1":
                new InventoryBasic(streamV3ApiRetailer, streamId).begin();
                break;

            case "2":
                int numberOfConsumers = Integer.parseInt(getConsoleInput("\nHow many concurrent consumers > "));
                int queueSize = Integer.parseInt(getConsoleInput("\nQueue size> "));
                new InventoryFanout(streamV3ApiRetailer, streamId).begin(numberOfConsumers, queueSize);
                break;

            case "3":
                String startPosition = getConsoleInput("\nStream Start Position > ");
                String endPosition = getConsoleInput("Stream End Position > ");
                new InventoryErrorRecovery(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin(startPosition, endPosition);
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
                "6) Order Stream\n" +
                " > "
        );

        String streamId = getConsoleInput("\nstreamId > ");
        int numPartitions = Integer.parseInt(getConsoleInput("\nnumPartitions > "));

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

            case "6":
                query.put("queryType", StreamV3Api.ObjectType.order);
                query.put("statuses", Arrays.asList("created", "shipment_pending", "shipped", "cancelled"));
                break;
        }

        //see if the stream has been created. if not, create it.
        if (!doesStreamExist(streamId, streamV3ApiRetailer, logger)) {
            createStream(streamId, numPartitions, streamV3ApiRetailer, logger, query);

            //it can sometimes take a bit of time before the stream becomes available; wait for it
            while (!doesStreamExist(streamId, streamV3ApiRetailer, logger)) {
                logger.info("stream not yet created. waiting a bit and checking again...");
                Thread.sleep(2_000L);
            }
        }

        begin();
    }

    private void doUpdateStreamPartitionSize()
    throws Exception
    {
        String streamId = getConsoleInput("\nstreamId > ");
        int numPartitions = Integer.parseInt(getConsoleInput("\nnumPartitions > "));
        boolean incrementVersionNumber = false; //non-demo you'd want to ask about this

        new UpdateStreamPartitionSize(streamV3ApiRetailer, streamId).execute(
                new UpdateStreamPartitionSize.Data(numPartitions, incrementVersionNumber));

        begin();
    }

    private void doCauseActivityOnStream()
    throws Exception
    {
        String streamType = getConsoleInput(
                "\n1) Update ItemInventory\n" +
                        "2) Create and acknowledge Order\n" +
                        "3) Create Invoice and Shipment\n" +
                        "4) Cancel order line item\n" +
                        "5) Do Inventory Sync\n" +
                        "6) Add shipment\n" +
                        //"7) Mark shipment undeliverable\n" +
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
                order = null;
                invoice = null;
                order = new OrderCreateAndAck(
                        retailerAccountId, inventoryV3ApiSupplier, orderV3ApiRetailer, orderV3ApiSupplier, skus
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
                if (order == null) {
                    System.out.println("\nyou must first create an order and an invoice");
                } else {
                    new OrderCancelItem(orderV3ApiSupplier).begin(order, invoice);
                }
            }
            break;

            case "5": {
                String streamId = getConsoleInput("\nstreamId > ");
                String uniqueIdentifierKey = "sku"; //getConsoleInput("\nItemInventory unique identifier to display (ex: sku, ean, gtin, isbn, mpn, upc, etc) > ");
                new InventorySync(streamV3ApiRetailer, streamId, uniqueIdentifierKey).begin();
            }
            break;

            case "6": {
                if (order == null) {
                    System.out.println("\nyou must first create an order");
                } else {
                    new OrderCreateShipment(orderV3ApiSupplier).begin(order);
                }
            }
            break;

//            case "7": {
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

    private void doViewStreams()
    throws Exception
    {
        String selection = getConsoleInput(
                "Type of stream?\n" +
                    "1) View Invoice Stream\n" +
                    "2) View Cancelled Stream\n" +
                    "3) View Undeliverable Shipment Stream\n" +
                    "4) View Shipped Stream\n" +
                    "5) View Partitioned Stream\n" +
                    "6) View Order Stream\n" +
                    " > "
        );

        String streamId = getConsoleInput("\nstreamId > ");

        switch (selection)
        {
            case "1":
                new AnyStreamBasic(GetAnyEventsFromPosition.Type.Invoice, streamV3ApiRetailer, streamId).begin();
                break;

            case "2":
                new AnyStreamBasic(GetAnyEventsFromPosition.Type.Cancelled, streamV3ApiRetailer, streamId).begin();
                break;

            case "3":
                new AnyStreamBasic(GetAnyEventsFromPosition.Type.UndeliverableShipment, streamV3ApiRetailer, streamId).begin();
                break;

            case "4":
                new AnyStreamBasic(GetAnyEventsFromPosition.Type.Shipped, streamV3ApiRetailer, streamId).begin();
                break;

            case "5":
                new PartitionConsumer(streamV3ApiRetailer, streamId).begin();
                break;

            case "6":
                new AnyStreamBasic(GetAnyEventsFromPosition.Type.Order, streamV3ApiRetailer, streamId).begin();
                break;

        }

        begin();
    }

    private void begin()
    throws Exception
    {
        //display the top level menu
        String selection = getConsoleInput(
    "\n" +
            "1) Create Stream                   (as retailer)\n" +
            "2) Update Stream Partition Size    (as retailer)\n" +
            "3) Cause activity on Stream\n" +
            "4) Inventory Stream Processing     (as retailer)\n" +
            "5) View Streams                    (as retailer)\n" +
            "6) Create Stream Operation         (as retailer)\n" +
            " > "
        );
        switch (selection)
        {
            case "1":
                doCreateStream();
                break;

            case "2":
                doUpdateStreamPartitionSize();
                break;

            case "3":
                doCauseActivityOnStream();
                break;

            case "4":
                doInventoryStreamProcessing();
                break;

            case "5":
                doViewStreams();
                break;

            case "6": {
                String streamId = getConsoleInput("\nstreamId > ");
                StreamV3Api.OperationType operationType = StreamV3Api.OperationType.valueOf(getConsoleInput("\noperationType [sync, setpartitionowner] > "));

                CreateStreamOperation createStreamOperationCmd;
                if (operationType == StreamV3Api.OperationType.sync) {

                    createStreamOperationCmd = new CreateStreamOperation(streamV3ApiRetailer, streamId, operationType, null, null);

                } else if (operationType == StreamV3Api.OperationType.setpartitionowner) {
                    int partitionId = Integer.parseInt(getConsoleInput("\npartitionId > "));
                    String ownerId = getConsoleInput("\nownerId > ");

                    createStreamOperationCmd = new CreateStreamOperation(streamV3ApiRetailer, streamId, operationType, partitionId, ownerId);

                } else {
                    throw new IllegalStateException("invalid operationType");
                }


                String operationUuid = createStreamOperationCmd.execute(null);
                logger.info("operationUuid: " + operationUuid);
            }
            break;

        }

    }

    //https://github.com/OpenUnirest/object-mappers-gson/blob/master/src/main/java/unirest/GsonObjectMapper.java
    static class GsonObjectMapper implements ObjectMapper
    {
        private Gson om;

        public GsonObjectMapper() {
            this(new Gson());
        }

        public GsonObjectMapper(Gson om) {
            this.om = om;
        }

        @Override
        public <T> T readValue(String value, Class<T> valueType) {
            return om.fromJson(value, valueType);
        }

        @Override
        public <T> T readValue(String value, GenericType<T> genericType) {
            return om.fromJson(value, genericType.getType());
        }

        @Override
        public String writeValue(Object value) {
            return om.toJson(value);
        }
    }

    public static void main(String[] args)
    {
        //System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
        //System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");
        try {
            //Unirest.config().verifySsl(false);

            //this will let unirest use our custom gson mapper that knows how to deal with Iso8601DateTime objects
            Unirest.config().setObjectMapper(new GsonObjectMapper(Util.gson()));

            new StreamsDemonstration().begin();

        } catch (Throwable e) {
            logger.error("unexpected error", e);

        } finally {
            if (Unirest.isRunning()) Unirest.shutDown();
        }
    }

}
