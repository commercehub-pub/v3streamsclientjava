package io.dsco.demo.scenario;

import io.dsco.demo.scenario.base.BasicStreamProcessor;
import io.dsco.demo.scenario.base.CommonStreamMethods;
import io.dsco.demo.scenario.base.ItemInventoryMethods;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

public class InventoryBasic
implements CommonStreamMethods
{
    private static final Logger logger = LogManager.getLogger(InventoryBasic.class);
    private static final String SCENARIO_NAME = "Basic Inventory Stream Processing";

    private final StreamV3Api streamV3Api;
    private final String streamId;

    private final BasicStreamProcessor<StreamItemInventory> basicStreamProcessor;
    private final ItemInventoryMethods itemInventoryMethods;

    public InventoryBasic(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;

        basicStreamProcessor = new BasicStreamProcessor<>(logger, streamV3Api, streamId);
        itemInventoryMethods = new ItemInventoryMethods(streamV3Api, streamId, uniqueIdentifierKey, logger);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //get the initial stream position
            String streamPosition = getStreamPosition(streamV3Api, streamId, logger);
            logger.info("initial stream position: " + streamPosition);

            basicStreamProcessor.processAllItemsInStream(streamPosition, itemInventoryMethods::getItemInventoryEventsFromPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

}
