package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.ItemInventory;
import io.dsco.stream.domain.StreamItemInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Scenario 2 will process all items in a stream, but it will do it in parallel. However, in order to prevent things
 * being pulled out of the queue and processing out of order, each record of the same item will always go to the same
 * consumer to be processed.
 */
public class InventoryFanout
extends BaseScenario
{
    private static final Logger logger = LogManager.getLogger(InventoryFanout.class);

    private static final String SCENARIO_NAME = "Fan-out Inventory Stream Processing";
    private static final int QUEUE_SIZE = 50;

    private List<BlockingQueue<StreamItemInventory>> queues;
    private List<Consumer> consumers;
    private CountDownLatch countDownLatch;
    private List<QueuePositionOrganizer> processedPositionList = new ArrayList<>();
    private Lock listLock = new ReentrantLock();

    private final BasicStreamProcessor<StreamItemInventory> basicStreamProcessor;

    public InventoryFanout(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        super(streamV3Api, streamId, uniqueIdentifierKey, logger);

        basicStreamProcessor = new BasicStreamProcessor<>(logger, streamV3Api, streamId);
    }

    public void begin(int numberOfConsumers)
    {
        try {
            long b = System.currentTimeMillis();

            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            queues = new ArrayList<>(numberOfConsumers);
            consumers = new ArrayList<>(numberOfConsumers);
            countDownLatch = new CountDownLatch(numberOfConsumers);

            //spin up the consumers
            for (int i = 0; i < numberOfConsumers; i++) {
                queues.add(new ArrayBlockingQueue<>(QUEUE_SIZE));
                Consumer consumer = new Consumer(this, queues.get(i));
                consumers.add(consumer);

                new Thread(consumer).start();
            }

            //get the initial stream position
            String streamPosition = getStreamPosition();
            logger.info("initial stream position: " + streamPosition);
            processAllItemsInStream(streamPosition);

            //wait for all the consumer threads to complete
            countDownLatch.await();

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));

            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private void processAllItemsInStream(String streamPosition)
    throws InterruptedException, ExecutionException
    {
        List<StreamItemInventory> items = getStreamEventsFromPosition(streamPosition);

        if (items.size() == 0) {
            //now that the stream is drained, let each consumer know it can shut down when processing is finished
            consumers.forEach(Consumer::notifyOkToShutdown);

            return; //all done
        }

        //pass the data off to the consumers for processing
        logger.info("sending items to consumer queues...");
        addItemsToQueue(items);

        //do it again, from the last known position
        processAllItemsInStream(items.get(items.size()-1).getId());
    }

    private void markInventoryItemAsProcessed(String position)
    throws InterruptedException, ExecutionException
    {
        listLock.lock();
        try {
            if (processedPositionList.get(0).position.equals(position)) {
                //if the item happens to be the first item in the stream, mark it as the current position
                // and also keep looping the list marking each successive item as the current position until we either
                // reach the end of the stream or we run into an item that hasn't been processed yet

                String updateStreamPositionToHere = processedPositionList.get(0).position;
                //int numItemsRemoved = 1;

                //updateStreamPosition(processedPositionList.get(0).position);
                processedPositionList.remove(0);

                while (processedPositionList.size() > 0 && processedPositionList.get(0).processed) {
                    //updateStreamPosition(processedPositionList.get(0).position);
                    updateStreamPositionToHere = processedPositionList.get(0).position;
                    //numItemsRemoved++;
                    processedPositionList.remove(0);
                }

                //now that one (or more) items have been removed, update the stream position to the last item which was
                // removed. this allows us to only call updateStreamPosition one time instead of many, if there were many
                // items that have now been contiguously completed
                //updateStreamPosition(updateStreamPositionToHere);
                basicStreamProcessor.updateStreamPosition(updateStreamPositionToHere);

                //logger.info(">>> " + numItemsRemoved + " were marked with only 1 call");

            } else {
                //if, however, the item in question is NOT the first item in the queue, then walk the queue until we find
                // it, and mark it as being ready to be marked once all items in front of it complete.
                for (QueuePositionOrganizer queueItem : processedPositionList) {
                    if (queueItem.position.equals(position)) {
                        queueItem.processed = true;
                        break;
                    }
                }
            }
        } finally {
            listLock.unlock();
        }
    }

    /**
     * This algorithm will take the primary identifier and hash the value. It will then take that modulus the number of available
     * consumers so it will always route the same item to the same consumer.
     */
    private void addItemsToQueue(List<StreamItemInventory> items)
    throws InterruptedException
    {
        for (StreamItemInventory streamItemInventory : items) {
            ItemInventory item = streamItemInventory.getItemInventory();

            //determine which queue gets the item
            //longstanding java math bug, this can produce a negative number if the hashcode is negative
            //int consumerIdx = item.getSku().hashCode() % queues.size();
            int consumerIdx = Math.floorMod(item.getSku().hashCode(), queues.size()); //java8 to the rescue

            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("sending item {0} to queue {1}", item.getSku(), consumerIdx));
            }

            //add the id of the item to the organizer queue so it can keep proper order when updating stream position
            listLock.lock();
            try {
                processedPositionList.add(new QueuePositionOrganizer(streamItemInventory.getId()));
            } finally {
                listLock.unlock();
            }

            //add the item to the queue. since these are bounded blocking queues, this will apply backpressure
            // by waiting until there is room in the queue if it fills up.
            queues.get(consumerIdx).put(streamItemInventory);
        }
    }

    /**
     * Do the work of pulling items off the processing queues and processing them. Once processed, update the
     * stream position.
     */
    private static final class Consumer
    implements Runnable
    {
        private InventoryFanout tester;
        private BlockingQueue<StreamItemInventory> queue;
        private boolean running = true;

        Consumer(InventoryFanout tester, BlockingQueue<StreamItemInventory> queue)
        {
            this.tester = tester;
            this.queue = queue;
        }

        void notifyOkToShutdown()
        {
            running = false;
            if (queue.isEmpty()) {
                //logger.info("*** adding special marker item to cause stream shutdown ***");
                //since the queue is empty and won't be receiving any more items, it is waiting. therefore put a special
                // item in the queue so the thread will resume, but it will know to ignore the special item and
                // terminate the loop
                try {
                    queue.put(new StreamItemInventory("-1", null, null));
                } catch (InterruptedException ignored) {
                }
            }
        }

        @Override
        public void run()
        {
            try {
                while (!queue.isEmpty() || running) {
                    //grab the next item from the queue. this will block until an item becomes available
                    StreamItemInventory streamItemInventory = queue.take();

                    //if this is the special marker item, ignore it and break the loop
                    if (streamItemInventory.getId().equals("-1")) break;

                    //process the item
                    tester.basicStreamProcessor.processItem(streamItemInventory);

                    //mark the item as processed
                    tester.markInventoryItemAsProcessed(streamItemInventory.getId());
                }
            } catch (InterruptedException ignored) {
            } catch (ExecutionException e) {
                logger.error("uncaught exception while processing item", e);
            }

            //let the caller know this consumer is complete
            tester.countDownLatch.countDown();
        }
    }

    /**
     * data container class to help order the position updates
     */
    private static final class QueuePositionOrganizer
    {
        String position;
        boolean processed;

        QueuePositionOrganizer(String position)
        {
            this.position = position;
        }
    }

}
