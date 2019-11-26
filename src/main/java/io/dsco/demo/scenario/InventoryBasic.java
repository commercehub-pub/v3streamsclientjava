package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

public class InventoryBasic
extends BaseScenario
{
    private static final Logger logger = LogManager.getLogger(InventoryBasic.class);
    private static final String SCENARIO_NAME = "Basic Inventory Stream Processing";

    private final BasicStreamProcessor<StreamItemInventory> basicStreamProcessor;

    public InventoryBasic(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        super(streamV3Api, streamId, uniqueIdentifierKey, logger);

        basicStreamProcessor = new BasicStreamProcessor<>(logger, streamV3Api, streamId);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //get the initial stream position
            String streamPosition = getStreamPosition();
            logger.info("initial stream position: " + streamPosition);

            //processAllItemsInStream(streamPosition);
            basicStreamProcessor.processAllItemsInStream(streamPosition, this::getStreamEventsFromPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

}
