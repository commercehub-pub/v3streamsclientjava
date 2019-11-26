package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class InventorySync
extends BaseScenario
{
    private static final Logger logger = LogManager.getLogger(InventorySync.class);

    private static final String SCENARIO_NAME = "Sync Stream Creation";

    private final BasicStreamProcessor<StreamItemInventory> basicStreamProcessor;

    public InventorySync(@NotNull StreamV3Api streamV3Api, @NotNull String streamId, @NotNull String uniqueIdentifierKey)
    {
        super(streamV3Api, streamId, uniqueIdentifierKey, logger);

        basicStreamProcessor = new BasicStreamProcessor<>(logger, streamV3Api, streamId);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            /*String operationUuid =*/ createStreamSync();

            //get the initial stream position
            String streamPosition = getStreamPosition();
            logger.info("initial stream position: " + streamPosition);

            basicStreamProcessor.processAllItemsInStream(streamPosition, this::getStreamEventsFromPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

}
