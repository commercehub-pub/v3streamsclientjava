package io.dsco.demo.scenario;

import io.dsco.demo.Util;
import io.dsco.stream.api.InventoryV2Api;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.retailer.CreateOrder;
import io.dsco.stream.command.supplier.AcknowledgeOrder;
import io.dsco.stream.command.supplier.GetOrder;
import io.dsco.stream.domain.*;
import io.dsco.stream.shared.GetInventoryItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class OrderCreateAndAck
implements GetInventoryItems
{
    private static final Logger logger = LogManager.getLogger(OrderCreateAndAck.class);
    private static final String SCENARIO_NAME = "Order Creation";

    private final InventoryV2Api inventoryApiSupplier;
    private final String retailerAccountId;
    private final CreateOrder createOrderCmd;
    private final AcknowledgeOrder acknowledgeOrderCmd;
    private final GetOrder getOrderCmd;

    public OrderCreateAndAck(
            String retailerAccountId, InventoryV2Api inventoryApiSupplier, OrderV3Api orderV3ApiRetailer,
            OrderV3Api orderV3ApiSupplier)
    {
        this.retailerAccountId = retailerAccountId;
        this.inventoryApiSupplier = inventoryApiSupplier;
        createOrderCmd = new CreateOrder(orderV3ApiRetailer);
        acknowledgeOrderCmd = new AcknowledgeOrder(orderV3ApiSupplier);
        getOrderCmd = new GetOrder(orderV3ApiSupplier);
    }

    public Order begin()
    throws Exception
    {
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

        //shuffle the list and pick the first 3
        Collections.shuffle(itemInventoryList);
        ItemInventory item1 = itemInventoryList.get(0);
        ItemInventory item2 = itemInventoryList.get(1);
        ItemInventory item3 = itemInventoryList.get(2);

        //create an order (retailer)
        Order order = createOrderObject(Arrays.asList(item1, item2, item3), retailerAccountId);
        CreateOrderResponse createOrderResponse = createOrderCmd.execute(order);

        //for the demo we'll assume just the one order
        String orderId = createOrderResponse.getDscoOrderIds().get(0);
        order.setDscoOrderId(orderId);

        logger.info("*** PO_NUMBER: " + order.getPoNumber());
        logger.info("*** DSCO_ORDER_ID: " + order.getDscoOrderId());

        //wait until the order exists (call getOrder)
        int httpResponse = -1;
        while (httpResponse != 200) {
            httpResponse = getOrderCmd.execute(new GetOrderRequest(
                    GetOrderRequest.OrderKey.dscoOrderId, order.getDscoOrderId(), null, null)
            );

            if (httpResponse != 200) {
                Thread.sleep(1_000L);
            }
        }

        //acknowledge the order (supplier)
        List<OrderAcknowledge> ordersToAcknowledge = Collections.singletonList(
                new OrderAcknowledge(order.getPoNumber(), OrderAcknowledge.Type.PO_NUMBER, null)
        );
        acknowledgeOrderCmd.execute(ordersToAcknowledge);

        long e = System.currentTimeMillis();
        logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
        logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        return order;
    }

    private Order createOrderObject(List<ItemInventory> items, String retailerAccountId)
    {
        String poNumber = new SimpleDateFormat("yyMMddhmmss").format(new Date());

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
                "84043", "UT", "US"
        );

        String shippingServiceLevelCode = "UPCG";

        return new Order(lineItems, poNumber, shipping, billTo, expectedDeliveryDate, shippingServiceLevelCode);
    }

}
