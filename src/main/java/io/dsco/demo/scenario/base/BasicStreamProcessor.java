package io.dsco.demo.scenario.base;

import io.dsco.demo.scenario.base.CommonStreamMethods;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItem;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * a class rather than an interface because it needed variables.
 *
 * Core functionality for consuming any type of a stream using the basic stream pattern.

 */
public class BasicStreamProcessor<T extends StreamItem>
implements CommonStreamMethods
{
    private static final long STREAM_UPDATE_INTERVAL = 5_000L;

    private final StreamV3Api streamV3Api;
    private final Logger logger;
    private final String streamId;

    private long lastStreamPositionUpdate = 0L;

    public BasicStreamProcessor(@NotNull Logger logger, @NotNull StreamV3Api streamV3Api, @NotNull String streamId)
    {
        this.logger = logger;
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    public void processAllItemsInStream(@NotNull String streamPosition, @NotNull Function<String, List<T>> streamRetriever)
    {
        try {
            List<T> items = streamRetriever.apply(streamPosition);

            if (items.size() == 0) return; //all done

            //process each item
            for (T item : items) {
                processItem(item);

                //per the best practices suggestion, only update the stream position periodically.
                if (System.currentTimeMillis() > lastStreamPositionUpdate + STREAM_UPDATE_INTERVAL) {
                    updateStreamPosition(streamV3Api, streamId, item.getId(), logger);
                    lastStreamPositionUpdate = System.currentTimeMillis();
                }
            }

            //do it again, from the last known position
            processAllItemsInStream(items.get(items.size()-1).getId(), streamRetriever);

        } catch (InterruptedException | ExecutionException e) {
            logger.error("unexpected error", e);
        }
    }

    public void processItem(T item)
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
