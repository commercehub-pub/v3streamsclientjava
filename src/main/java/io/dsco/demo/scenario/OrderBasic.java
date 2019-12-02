package io.dsco.demo.scenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

public class OrderBasic
{
    private static final Logger logger = LogManager.getLogger(OrderBasic.class);
    private static final String SCENARIO_NAME = "Basic Order Processing";

    private final String streamId;

    public OrderBasic(String streamId)
    {
        this.streamId = streamId;
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //TODO: create an order (retailer) - must be a valid item from their supplier

            //TODO: supplier exports/acknowledges the order

            //TODO: create an invoice for the (supplier)

            //TODO: cancel an item on the order (supplier)

            //TODO: ship the order (supplier)

            //TODO: mark the shipment as undeliverable (supplier)

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }
}
