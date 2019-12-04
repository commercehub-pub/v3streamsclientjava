package io.dsco.demo.scenario;

import io.dsco.demo.Util;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class OrderBasic
implements GetInventoryItems
{
    private static final Logger logger = LogManager.getLogger(OrderBasic.class);
    private static final String SCENARIO_NAME = "Basic Order Processing";

    private final InventoryV2Api inventoryApiSupplier;
    private final CreateOrder createOrderCmd;
    private final AcknowledgeOrder acknowledgeOrderCmd;
    private final CreateInvoiceSmallBatch createInvoiceSmallBatchCmd;
    private final String retailerAccountId;

    public OrderBasic(
            InventoryV2Api inventoryApiSupplier, OrderV3Api orderV3ApiRetailer, OrderV3Api orderV3ApiSupplier,
            InvoiceV3Api invoiceV3ApiSupplier, String retailerAccountId)
    {
        this.inventoryApiSupplier = inventoryApiSupplier;
        createOrderCmd = new CreateOrder(orderV3ApiRetailer);
        acknowledgeOrderCmd = new AcknowledgeOrder(orderV3ApiSupplier);
        createInvoiceSmallBatchCmd = new CreateInvoiceSmallBatch(invoiceV3ApiSupplier);
        this.retailerAccountId = retailerAccountId;
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
            if (itemInventoryList.isEmpty()) {
                throw new IllegalStateException("no items from supplier");
            }
            if (itemInventoryList.size() < 3) {
                throw new IllegalStateException("not enough items for the demo");
            }
            ItemInventory item1 = itemInventoryList.get(new Random(System.currentTimeMillis()).nextInt(itemInventoryList.size()));
            ItemInventory item2 = itemInventoryList.get(new Random(System.currentTimeMillis()).nextInt(itemInventoryList.size()));
            ItemInventory item3 = itemInventoryList.get(new Random(System.currentTimeMillis()).nextInt(itemInventoryList.size()));

            //create an order (retailer)
            Order order = createOrderObject(Arrays.asList(item1, item2, item3), retailerAccountId);
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

            //TODO: branch and either:
            // 1 - supplier - cancel an item on the order
            // 2 - supplier - cancel - same as #1, but just cancel all the line items
            // 3 - supplier - returns - use v2 - https://apis.dsco.io/docs/v2/#!/order/updateOrderShipment (same shipment data with a few extra flags):
            //      flags are: returnedFlag, returnNumber, returnReason, returnDate

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private Order createOrderObject(List<ItemInventory> items, String retailerAccountId)
    {
        String poNumber = UUID.randomUUID().toString();

        //3 days from now
        String expectedDeliveryDate =
            Util.dateToIso8601(Date.from(LocalDate.now().plusDays(3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        //create the line items
        List<OrderLineItem> lineItems = new ArrayList<>(items.size());
        int lineNumber = 0;
        for (ItemInventory item : items) {
            lineNumber++;

            //use the partnerSkuMap to get the appropriate partnerSku
            List<PartnerSkuMap> partnerSkus = item.getPartnerSkuMap();
            String partnerSku = null;
            for (PartnerSkuMap skuItem : partnerSkus) {
                if (skuItem.getDscoRetailerId().equals(retailerAccountId)) {
                    partnerSku = skuItem.getPartnerSku();
                    break;
                }
            }
            if (partnerSku == null) {
                throw new IllegalStateException(MessageFormat.format(
                    "no partnerSku found for item {0}, retailerAccountId: {1}", item.getDscoItemId(), retailerAccountId));
            }

            lineItems.add(new OrderLineItem(1, lineNumber, item.getDscoItemId(), item.getEan(), partnerSku, item.getSku(), item.getUpc()));
        }

        OrderShipping shipping = new OrderShipping(
                "3900 Traverse Mountain Blvd",
                "Lehi",
                "Dsco", "the Company",
                "84043", "UT"
        );

        OrderBillTo billTo = new OrderBillTo(
                "3900 Traverse Mountain Blvd",
                "Lehi",
                "Dsco", "the Company",
                "84043", "UT"
        );

        return new Order(lineItems, poNumber, shipping, billTo, expectedDeliveryDate);
    }

    private InvoiceForUpdate createInvoiceObject(Order order)
    {
        String invoiceId = UUID.randomUUID().toString();
        float totalAmount = 0.0F;

        List<InvoiceLineItemForUpdate> lineItems = new ArrayList<>(order.getLineItems().size());
        for (OrderLineItem lineItem : order.getLineItems()) {
            totalAmount += lineItem.getConsumerPrice();
            lineItems.add(new InvoiceLineItemForUpdate(
                    lineItem.getQuantity(), lineItem.getDscoItemId(), lineItem.getEan(), lineItem.getPartnerSku(), lineItem.getSku(), lineItem.getUpc())
            );
        }

        List<InvoiceCharge> charges = Collections.singletonList(
                new InvoiceCharge(totalAmount, "everything")
        );

        return new InvoiceForUpdate(order.getPoNumber(), invoiceId, totalAmount, charges, lineItems);
    }

    private void createShipment()
    {

    }

}
