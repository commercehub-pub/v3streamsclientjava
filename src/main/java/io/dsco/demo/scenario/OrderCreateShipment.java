package io.dsco.demo.scenario;

import io.dsco.demo.DomainFactory;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.supplier.CreateShipmentSmallBatch;
import io.dsco.stream.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderCreateShipment
{
    private static final Logger logger = LogManager.getLogger(OrderCreateShipment.class);
    private static final String SCENARIO_NAME = "Order Create Shipment";

    private final CreateShipmentSmallBatch createShipmentSmallBatchCmd;

    public OrderCreateShipment(OrderV3Api orderV3Api)
    {
        createShipmentSmallBatchCmd = new CreateShipmentSmallBatch(orderV3Api);
    }

    public void begin(Order order)
    throws Exception
    {
        long b = System.currentTimeMillis();
        logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

        List<ShipmentsForUpdate> shipments = getOrderShipments(order);

        //make the call
        SyncUpdateResponse response = createShipmentSmallBatchCmd.execute(shipments);

        //make sure the async response was a success
        if (response.getStatus() == SyncUpdateResponse.STATUS.FAILURE) {
            //for non-demo, you'd want to check the messages array to find what went wrong
            throw new IllegalStateException("unable to create shipment");
        }

        long e = System.currentTimeMillis();
        logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
        logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));
    }

    private List<ShipmentsForUpdate> getOrderShipments(Order order)
    {
        //for demo purposes, assume all line items in the order were shipped in the same shipment.

        List<ShipmentLineItemForUpdate> lineItems = order.getLineItems().stream()
            .map(li -> {
                return DomainFactory.shipmentLineItemForUpdate(li.getQuantity(), li.getDscoItemId(), li.getEan(), li.getLineNumber(), li.getPartnerSku(), li.getSku(), li.getUpc());
            })
            .collect(Collectors.toList());

        String trackingNumber = UUID.randomUUID().toString();
        List<ShipmentForUpdate> shipments = Collections.singletonList(DomainFactory.shipmentForUpdate(lineItems, trackingNumber));

        ShipmentsForUpdate shipment = DomainFactory.shipmentsForUpdate(
                order.getDscoOrderId(), order.getPoNumber(), shipments, order.getSupplierOrderNumber());

        return Collections.singletonList(shipment);
    }
}
