package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.retailer.GetItemInventoryEventsFromPosition;
import io.dsco.stream.command.retailer.ProcessItemInventoryStream;
import io.dsco.stream.shared.CommonStreamMethods;
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

    private final ProcessItemInventoryStream processItemInventoryStreamCmd;

    public InventoryBasic(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;

        GetItemInventoryEventsFromPosition getItemInventoryEventsFromPositionCmd =
                new GetItemInventoryEventsFromPosition(streamV3Api, streamId, uniqueIdentifierKey);
        processItemInventoryStreamCmd = new ProcessItemInventoryStream(streamV3Api, streamId, getItemInventoryEventsFromPositionCmd);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //get the initial stream position
            String streamPosition = getStreamPosition(streamV3Api, streamId, logger);
            logger.info("initial stream position: " + streamPosition);

            processItemInventoryStreamCmd.execute(streamPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

}
