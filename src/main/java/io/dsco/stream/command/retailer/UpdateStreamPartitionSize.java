package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.shared.NetworkExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class UpdateStreamPartitionSize
implements Command<UpdateStreamPartitionSize.Data, Void>
{
    private static final Logger logger = LogManager.getLogger(UpdateStreamPartitionSize.class);
    private final StreamV3Api streamV3Api;
    private final java.lang.String streamId;

    public UpdateStreamPartitionSize(StreamV3Api streamV3Api, String streamId)
    {
        this.streamId = streamId;
        this.streamV3Api = streamV3Api;
    }

    public static class Data
    {
        public int numPartitions;
        public boolean incrementVersionNumber;

        public Data(int numPartitions, boolean incrementVersionNumber)
        {
            this.numPartitions = numPartitions;
            this.incrementVersionNumber = incrementVersionNumber;
        }
    }

    @Override
    public Void execute(@NotNull Data data)
    throws Exception
    {
        /*CompletableFuture<HttpResponse<JsonNode>> future =*/ NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.updateStream(streamId, null, data.numPartitions, data.incrementVersionNumber, null);
        }, streamV3Api, logger, "updateStream-numPartitions", NetworkExecutor.HTTP_RESPONSE_200);

        return null;
    }
}
