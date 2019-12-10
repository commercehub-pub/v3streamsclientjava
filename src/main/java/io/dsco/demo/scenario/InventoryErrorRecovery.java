package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.retailer.GetItemInventoryEventsInRange;
import io.dsco.stream.domain.StreamEventInventory;
import io.dsco.stream.shared.ItemInventoryProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

//TODO: convert to StreamItem
public class InventoryErrorRecovery
implements ItemInventoryProcessor
{
    private static final String SCENARIO_NAME = "Error Recovery Inventory Stream Processing";
    private static final Logger logger = LogManager.getLogger(InventoryErrorRecovery.class);

    private final GetItemInventoryEventsInRange getItemInventoryEventsInRangeCmd;

    //if the range of the stream values is too large to return in one query, it will need to repeat until the desired
    // end position is reached
    private String desiredEndPosition;

    public InventoryErrorRecovery(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        getItemInventoryEventsInRangeCmd = new GetItemInventoryEventsInRange(streamV3Api, streamId, uniqueIdentifierKey);
    }

    public void begin(String startPosition, String endPosition)
    {
        desiredEndPosition = endPosition;

        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            processAllItemsInStream(startPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private void processAllItemsInStream(String startPosition)
    throws Exception
    {
        List<StreamEventInventory> items = getItemInventoryEventsInRangeCmd.execute(Arrays.asList(startPosition, desiredEndPosition));

        if (items.size() == 0) return; //all done

        for (StreamEventInventory item : items) {
            //processStreamInventoryItem(item);
            processItem(item, logger);
            //if you wish to maintain your own stream position in the stream, you could write it here to a file or database or whatever on your end
        }

        //if this stream didn't return all the items in the range (because the range was too large), call it again
        String endPosition = items.get(items.size()-1).getId();
        if (!endPosition.equals(desiredEndPosition)) {
            processAllItemsInStream(endPosition);
        }
    }
}
