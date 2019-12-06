package io.dsco.stream.shared;

import io.dsco.stream.domain.StreamItem;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Random;

public interface AnyProcessor
{
    default void processItem(StreamItem<?> item, Logger logger)
    throws InterruptedException
    {
        //this isn't actually going to do anything for the demo, other than wait a random amount of time from 25-100ms
        Thread.sleep(new Random().nextInt(75) + 25);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "completed processing of item {0}, key: {1}, source: {2}",
                    item.getId(), item.getKey(), item.getSource()));
        }
    }
}