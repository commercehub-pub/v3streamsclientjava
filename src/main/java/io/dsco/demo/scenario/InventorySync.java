package io.dsco.demo.scenario;

import io.dsco.demo.scenario.base.*;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class InventorySync
implements CommonStreamMethods, StreamSyncMethods
{
    private static final Logger logger = LogManager.getLogger(InventorySync.class);

    private static final String SCENARIO_NAME = "Sync Stream Creation";

    private final StreamV3Api streamV3Api;
    private final String streamId;

    private final BasicStreamProcessor<StreamItemInventory> basicStreamProcessor;
    private final ItemInventoryMethods itemInventoryMethods;

    public InventorySync(@NotNull StreamV3Api streamV3Api, @NotNull String streamId, @NotNull String uniqueIdentifierKey)
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

            /*String operationUuid =*/ createStreamSync(streamV3Api, streamId, logger);

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
