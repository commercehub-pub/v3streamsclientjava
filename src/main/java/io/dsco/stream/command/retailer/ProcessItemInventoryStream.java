package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamItemInventory;
import io.dsco.stream.shared.CommonStreamMethods;
import io.dsco.stream.shared.ItemInventoryProcessor;
import io.dsco.stream.shared.StreamItemInventoryBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class ProcessItemInventoryStream
implements Command<String, Void>, CommonStreamMethods, ItemInventoryProcessor
{
    private static final Logger logger = LogManager.getLogger(ProcessItemInventoryStream.class);
    private static final long STREAM_UPDATE_INTERVAL = 5_000L;

    private long lastStreamPositionUpdate = 0L;
    private StreamV3Api streamV3Api;
    private String streamId;

    private final StreamItemInventoryBase itemRetrieverCmd;

    public ProcessItemInventoryStream(StreamV3Api streamV3Api, String streamId, StreamItemInventoryBase itemRetrieverCmd)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
        this.itemRetrieverCmd = itemRetrieverCmd;
    }

    @Override
    public Void execute(String position) throws Exception
    {
        List<StreamItemInventory> items = itemRetrieverCmd.execute(Collections.singletonList(position));
        StreamItemInventory lastItem = null;

        while (items.size() > 0) {
            //process each item
            for (StreamItemInventory item : items) {
                processItem(item, logger);
                lastItem = item;

                //per the best practices suggestion, only update the stream position periodically.
                if (System.currentTimeMillis() > lastStreamPositionUpdate + STREAM_UPDATE_INTERVAL) {
                    updateStreamPosition(streamV3Api, streamId, item.getId(), logger);
                    lastStreamPositionUpdate = System.currentTimeMillis();
                }
            }

            //do a final position update for this batch of items
            updateStreamPosition(streamV3Api, streamId, lastItem.getId(), logger);

            //get the next batch of items
            items = itemRetrieverCmd.execute(Collections.singletonList(lastItem.getId()));
        }

        return null;
    }

}
