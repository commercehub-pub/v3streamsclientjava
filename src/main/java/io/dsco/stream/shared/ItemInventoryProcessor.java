package io.dsco.stream.shared;

import io.dsco.stream.domain.StreamEventInventory;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Random;

public interface ItemInventoryProcessor
{
    default void processItem(StreamEventInventory item, Logger logger)
    throws InterruptedException
    {
        //this isn't actually going to do anything for the demo, other than wait a random amount of time from 25-100ms
        Thread.sleep(new Random().nextInt(75) + 25);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "completed processing of item {0} at position {1}, source: {2}",
                    item.getKey(), item.getId(), item.getSource() ));
        }
    }
}
