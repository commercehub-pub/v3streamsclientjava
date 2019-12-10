package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.retailer.GetAnyEventsFromPosition;
import io.dsco.stream.command.retailer.ProcessAnyStream;
import io.dsco.stream.domain.Stream;
import io.dsco.stream.domain.StreamPartition;
import io.dsco.stream.shared.CommonStreamMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;

public class PartitionConsumer
implements CommonStreamMethods
{
    private static final Logger logger = LogManager.getLogger(PartitionConsumer.class);
    private static final String SCENARIO_NAME = "Partition Consumer Example";

    private CountDownLatch countDownLatch;

    private final StreamV3Api streamV3Api;
    private final String streamId;

    public PartitionConsumer(StreamV3Api streamV3Api, String streamId)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //grab each of the stream partitions
            Stream stream = getStreamPosition(streamV3Api, streamId, null, logger);
            logger.info(MessageFormat.format("there are {0} partitions in stream {1}", stream.getPartitions().size(), streamId));

            countDownLatch = new CountDownLatch(stream.getPartitions().size());

            //create a consumer for each partition
            for (StreamPartition streamPartition : stream.getPartitions()) {
                int partitionId = streamPartition.getPartitionId();
                String streamPosition = streamPartition.getPosition();

                new Thread(new Consumer(countDownLatch, streamV3Api, streamId, partitionId, streamPosition)).start();
            }

            //wait for everything to finish
            countDownLatch.await();

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private static final class Consumer
    implements Runnable
    {
        private CountDownLatch countDownLatch;
        private String streamPosition;

        private final ProcessAnyStream processAnyStreamCmd;

        public Consumer(
                CountDownLatch countDownLatch, StreamV3Api streamV3Api,
                String streamId, int partitionId, String streamPosition)
        {
            this.countDownLatch = countDownLatch;
            this.streamPosition = streamPosition;

            processAnyStreamCmd = new ProcessAnyStream(GetAnyEventsFromPosition.Type.Inventory, streamV3Api, streamId, partitionId);
        }

        @Override
        public void run()
        {
            //process everything in the stream
            try {
                processAnyStreamCmd.execute(streamPosition);
            } catch (Exception e) {
                logger.error("unable to process stream", e);
            }

            countDownLatch.countDown();
        }
    }
}
