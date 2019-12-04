package io.dsco.demo.scenario;

import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.retailer.CreateOrder;
import io.dsco.stream.command.supplier.AcknowledgeOrder;
import io.dsco.stream.command.supplier.CreateInvoiceSmallBatch;
import io.dsco.stream.domain.*;
import io.dsco.stream.shared.GetInventoryItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderBasic
implements GetInventoryItems
{
    private static final Logger logger = LogManager.getLogger(OrderBasic.class);
    private static final String SCENARIO_NAME = "Basic Order Processing";

    private final InventoryV2Api inventoryApiSupplier;
    private final CreateOrder createOrderCmd;
    private final AcknowledgeOrder acknowledgeOrderCmd;
    private final CreateInvoiceSmallBatch createInvoiceSmallBatchCmd;

    public OrderBasic(
            InventoryV2Api inventoryApiSupplier, OrderV3Api orderV3ApiRetailer, OrderV3Api orderV3ApiSupplier,
            InvoiceV3Api invoiceV3ApiSupplier)
    {
        this.inventoryApiSupplier = inventoryApiSupplier;
        createOrderCmd = new CreateOrder(orderV3ApiRetailer);
        acknowledgeOrderCmd = new AcknowledgeOrder(orderV3ApiSupplier);
        createInvoiceSmallBatchCmd = new CreateInvoiceSmallBatch(invoiceV3ApiSupplier);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //arbitrary date to limit stream to a smaller subset for demo purposes
            String updatedSince = "2019-08-15T18:26:00Z";

            //grab an item from the suppliers inventory to use (pick one at random)
            List<ItemInventory> itemInventoryList = getInventoryItems(inventoryApiSupplier, null, updatedSince, logger);
            ItemInventory item = itemInventoryList.get(new Random(System.currentTimeMillis()).nextInt(itemInventoryList.size()));

            //create an order (retailer)
            Order order = createOrderObject(item);
            String status = createOrderCmd.execute(order);
            if (!status.equals("success")) {
                throw new IllegalStateException("unable to create order");
            }

            //acknowledge the order (supplier)
            List<OrderAcknowledge> ordersToAcknowledge = Collections.singletonList(
                    new OrderAcknowledge(order.getDscoOrderId(), OrderAcknowledge.Type.DSCO_ORDER_ID, null));
            OrderAcknowledgeResponse acknowledgeResponse = acknowledgeOrderCmd.execute(ordersToAcknowledge);
            if (acknowledgeResponse.getStatus() != OrderAcknowledgeResponse.Status.success) {
                throw new IllegalStateException("unable to acknowledge order");
            }

            //create an invoice for the order (supplier)
            createInvoiceSmallBatchCmd.execute(Collections.singletonList(createInvoiceObject(order)));

            //TODO: cancel an item on the order (supplier)

            //TODO: branch and either:
            // 1 - supplier - mark the shipment as undeliverable (supplier) [v2 api: /v2/order/{dscoOrderId}/status/{status} ??]
            // 2 - supplier - mark the shipment as delivered (supplier) - is this a thing? i don't see a v3 api for it. another v2 api?

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private Order createOrderObject(ItemInventory item)
    {
        //TODO (make sure there are multiple line items so that one can be cancelled)
        String dscoOrderId = UUID.randomUUID().toString();

        return null;
    }

    private InvoiceForUpdate createInvoiceObject(Order order)
    {
        //TODO

        return null;
    }
}
