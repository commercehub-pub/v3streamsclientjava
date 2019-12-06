package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamItemInventory;
import io.dsco.stream.shared.AnyProcessor;
import io.dsco.stream.shared.CommonStreamMethods;
import io.dsco.stream.shared.ItemInventoryProcessor;
import io.dsco.stream.shared.StreamItemInventoryBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class ProcessAnyStream
implements Command<String, Void>, CommonStreamMethods, AnyProcessor
{
    private static final Logger logger = LogManager.getLogger(ProcessAnyStream.class);
    private static final long STREAM_UPDATE_INTERVAL = 5_000L;

    private final GetAnyEventsFromPosition getAnyEventsFromPositionCmd;

    private long lastStreamPositionUpdate = 0L;
    private StreamV3Api streamV3Api;
    private String streamId;

    public ProcessAnyStream(StreamV3Api streamV3Api, String streamId)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;

        getAnyEventsFromPositionCmd = new GetAnyEventsFromPosition(streamV3Api, streamId);
    }

    @Override
    public Void execute(String position) throws Exception
    {
        List<String> items = getAnyEventsFromPositionCmd.execute(position);
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", items.size()));
        }

        String lastItem = null;

        while (items.size() > 0) {
            //process each item
            for (String item : items) {
                processItem(item, logger);
                lastItem = item;

                //per the best practices suggestion, only update the stream position periodically.
                if (System.currentTimeMillis() > lastStreamPositionUpdate + STREAM_UPDATE_INTERVAL) {
                    updateStreamPosition(streamV3Api, streamId, item, logger);
                    lastStreamPositionUpdate = System.currentTimeMillis();
                }
            }

            //do a final position update for this batch of items
            updateStreamPosition(streamV3Api, streamId, lastItem, logger);

            //get the next batch of items
            items = getAnyEventsFromPositionCmd.execute(lastItem);
        }

        return null;
    }

}
