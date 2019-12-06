package io.dsco.demo.scenario;

import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.supplier.CancelOrderItemSmallBatch;
import io.dsco.stream.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class OrderCancelItem
{
    private static final Logger logger = LogManager.getLogger(OrderCancelItem.class);
    private static final String SCENARIO_NAME = "Order Cancel Line Item";

    private final CancelOrderItemSmallBatch cancelOrderItemSmallBatchCmd;

    public OrderCancelItem(OrderV3Api orderV3Api)
    {
        cancelOrderItemSmallBatchCmd = new CancelOrderItemSmallBatch(orderV3Api);
    }

    public void begin(Order order, InvoiceForUpdate invoice)
    throws Exception
    {
        long b = System.currentTimeMillis();
        logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

        List<io.dsco.stream.domain.OrderCancelItem> itemsToCancel =
                Collections.singletonList(getCancelItem(order, invoice));

        //make the call
        ResponseSmallBatch response = cancelOrderItemSmallBatchCmd.execute(itemsToCancel);

        //make sure the async response was a success
        if (response.getStatus() == ResponseSmallBatch.Status.failure) {
            //for non-demo, you'd want to check the messages array to find what went wrong
            throw new IllegalStateException("unable to cancel order item");
        }

        //TODO: check cancel item change log api (API not yet exposed)

        long e = System.currentTimeMillis();
        logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
        logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));
    }

    private io.dsco.stream.domain.OrderCancelItem getCancelItem(Order order, InvoiceForUpdate invoice)
    {
        //for demo just grab first line item to cancel
        OrderLineItem lineItem = order.getLineItems().get(0);

        List<OrderLineItemForCancel> lineItems = Collections.singletonList(new OrderLineItemForCancel(
                "CXRF", lineItem.getQuantity(), "because", lineItem.getDscoItemId(),
                lineItem.getDscoSupplierId(), lineItem.getDscoTradingPartnerId(), lineItem.getEan(),
                lineItem.getLineNumber(), lineItem.getPartnerSku(), lineItem.getSku(), lineItem.getUpc()
        ));

        return new io.dsco.stream.domain.OrderCancelItem(order.getDscoOrderId(), lineItems, io.dsco.stream.domain.OrderCancelItem.Type.DSCO_ORDER_ID);
    }
}
