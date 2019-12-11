package io.dsco.demo.scenario;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.retailer.GetAnyEventsFromPosition;
import io.dsco.stream.command.retailer.ProcessAnyStream;
import io.dsco.stream.domain.Stream;
import io.dsco.stream.shared.CommonStreamMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;

//show any type of stream. don't convert the result, just display the # of items in the stream and the position being processed.
public class AnyStreamBasic
implements CommonStreamMethods
{
    private static final Logger logger = LogManager.getLogger(CommonStreamMethods.class);
    private static final String SCENARIO_NAME = "Basic Any Stream Processing";

    private final StreamV3Api streamV3Api;
    private final String streamId;
    private final ProcessAnyStream processAnyStreamCmd;

    public AnyStreamBasic(GetAnyEventsFromPosition.Type type, StreamV3Api streamV3Api, String streamId)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
        processAnyStreamCmd = new ProcessAnyStream(type, streamV3Api, streamId, 0);
    }

    public void begin()
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //get the initial stream position
            Stream stream = getStreamPosition(streamV3Api, streamId, Collections.singletonList(0), logger);
            String streamPosition = stream.getPartitions().get(0).getPosition();
            logger.info("initial stream position: " + streamPosition);

            processAnyStreamCmd.execute(streamPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }
}
