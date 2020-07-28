package io.dsco.demo.scenario;

import io.dsco.demo.DomainFactory;
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
    private static final Logger logger = LogManager.getLogger(OrderForCancel.class);
    private static final String SCENARIO_NAME = "Order Cancel Line Item";

    private final CancelOrderItemSmallBatch cancelOrderItemSmallBatchCmd;

    public OrderCancelItem(OrderV3Api orderV3Api)
    {
        cancelOrderItemSmallBatchCmd = new CancelOrderItemSmallBatch(orderV3Api);
    }

    public void begin(Order order, Invoice invoice)
    throws Exception
    {
        long b = System.currentTimeMillis();
        logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

        List<io.dsco.stream.domain.OrderForCancel> itemsToCancel =
                Collections.singletonList(getCancelItem(order, invoice));

        //make the call
        SyncUpdateResponse response = cancelOrderItemSmallBatchCmd.execute(itemsToCancel);

        //make sure the async response was a success
        if (response.getStatus() == SyncUpdateResponse.STATUS.FAILURE) {
            //for non-demo, you'd want to check the messages array to find what went wrong
            throw new IllegalStateException("unable to cancel order item");
        }

        //TODO: check order change log api

        long e = System.currentTimeMillis();
        logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
        logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));
    }

    private OrderForCancel getCancelItem(Order order, Invoice invoice)
    {
        //for demo just grab first line item to cancel
        OrderLineItem lineItem = order.getLineItems().get(0);

        List<OrderLineItemForCancel> lineItems = Collections.singletonList(DomainFactory.orderLineItemForCancel(
                "CXRF", lineItem.getQuantity(), "because", lineItem.getDscoItemId(),
                lineItem.getDscoSupplierId(), lineItem.getDscoTradingPartnerId(), lineItem.getEan(),
                lineItem.getLineNumber(), lineItem.getPartnerSku(), lineItem.getSku(), lineItem.getUpc()
        ));

        return DomainFactory.orderForCancel(order.getDscoOrderId(), lineItems, io.dsco.stream.domain.OrderForCancel.TYPE.DSCO_ORDER_ID);
    }
}
