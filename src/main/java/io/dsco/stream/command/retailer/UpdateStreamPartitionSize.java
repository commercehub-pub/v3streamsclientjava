package io.dsco.stream.command.retailer;

import io.dsco.demo.Util;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
        // grab the existing stream to get the query/objectType values (which are required on the update but not asked for in the demo ui)
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.listStreams(streamId, null);
        }, streamV3Api, logger, "getStream", NetworkExecutor.HTTP_RESPONSE_200);

        //since we're calling listStreams, there will be an array of streams, even though it's just one stream
        // coming back since we passed in a streamId
        JSONObject obj = future.get().getBody().getArray().getJSONObject(0);
        final String objectType = obj.getString("objectType");
        final Map query = Util.gson().fromJson(String.valueOf(obj.getJSONObject("query")), Map.class);
        
        /*CompletableFuture<HttpResponse<JsonNode>> future =*/ NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.updateStream(streamId, null, data.numPartitions, data.incrementVersionNumber, objectType, query);
        }, streamV3Api, logger, "updateStream-numPartitions", NetworkExecutor.HTTP_RESPONSE_200);

        return null;
    }
}
