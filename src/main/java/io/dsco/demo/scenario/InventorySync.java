package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.retailer.CreateItemInventoryStreamSync;
import io.dsco.stream.command.retailer.GetItemInventoryEventsFromPosition;
import io.dsco.stream.command.retailer.ProcessItemInventoryStream;
import io.dsco.stream.domain.Stream;
import io.dsco.stream.shared.CommonStreamMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class InventorySync
implements CommonStreamMethods
{
    private static final Logger logger = LogManager.getLogger(InventorySync.class);

    private static final String SCENARIO_NAME = "Sync Stream Creation";

    private final StreamV3Api streamV3Api;
    private final String streamId;

    private final CreateItemInventoryStreamSync createItemInventoryStreamSyncCmd;
    private final ProcessItemInventoryStream processItemInventoryStreamCmd;

    public InventorySync(@NotNull StreamV3Api streamV3Api, @NotNull String streamId, @NotNull String uniqueIdentifierKey)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;

        createItemInventoryStreamSyncCmd = new CreateItemInventoryStreamSync(streamV3Api, streamId);

        GetItemInventoryEventsFromPosition getItemInventoryEventsFromPositionCmd =
                new GetItemInventoryEventsFromPosition(streamV3Api, streamId, uniqueIdentifierKey);
        processItemInventoryStreamCmd = new ProcessItemInventoryStream(streamV3Api, streamId, getItemInventoryEventsFromPositionCmd);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            /*String operationUuid =*/ createItemInventoryStreamSyncCmd.execute(null);

            //get the initial stream position
            Stream stream = getStreamPosition(streamV3Api, streamId, null, logger);
            String streamPosition = stream.getPartitions().get(0).getPosition();
            logger.info("initial stream position: " + streamPosition);

            processItemInventoryStreamCmd.execute(streamPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Throwable e) {
            logger.error("unhandled exception", e);
        }
    }

}
