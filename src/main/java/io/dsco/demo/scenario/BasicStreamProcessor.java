package io.dsco.demo.scenario;

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

class BasicStreamProcessor<T extends StreamItem>
{
    private static final long STREAM_UPDATE_INTERVAL = 5_000L;

    private final StreamV3Api streamV3Api;
    private final Logger logger;
    private final String streamId;

    private long lastStreamPositionUpdate = 0L;

    BasicStreamProcessor(@NotNull Logger logger, @NotNull StreamV3Api streamV3Api, @NotNull String streamId)
    {
        this.logger = logger;
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    void processAllItemsInStream(@NotNull String streamPosition, @NotNull Function<String, List<T>> streamRetriever)
    {
        try {
            List<T> items = streamRetriever.apply(streamPosition);

            if (items.size() == 0) return; //all done

            //process each item
            for (T item : items) {
                processItem(item);

                //per the best practices suggestion, only update the stream position periodically.
                if (System.currentTimeMillis() > lastStreamPositionUpdate + STREAM_UPDATE_INTERVAL) {
                    updateStreamPosition(item.getId());
                    lastStreamPositionUpdate = System.currentTimeMillis();
                }
            }

            //do it again, from the last known position
            processAllItemsInStream(items.get(items.size()-1).getId(), streamRetriever);

        } catch (InterruptedException | ExecutionException e) {
            logger.error("unexpected error", e);
        }
    }

    void processItem(T item)
    throws InterruptedException
    {
        //this isn't actually going to do anything for the demo, other than wait a random amount of time from 25-100ms
        Thread.sleep(new Random().nextInt(75) + 25);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "completed processing of item {0} at position {1}",
                    item.getKey(), item.getId() ));
        }
    }

    void updateStreamPosition(String streamPosition)
    throws ExecutionException, InterruptedException
    {
        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.updateStreamPosition(streamId, streamPosition);

        int httpStatus = future.get().getStatus();
        if (httpStatus != 200) {
            throw new IllegalStateException(MessageFormat.format(
                    "unable to update stream {0} to position {1}. http response code: {2}: ",
                    streamId, streamPosition, httpStatus));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("stream position updated to: {0}", streamPosition));
        }
    }
}
