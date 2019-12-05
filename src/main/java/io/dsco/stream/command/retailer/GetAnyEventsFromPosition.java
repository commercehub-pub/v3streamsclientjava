package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamItemInventory;
import io.dsco.stream.shared.NetworkExecutor;
import io.dsco.stream.shared.StreamItemInventoryBase;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetAnyEventsFromPosition
implements Command<String, List<String>>
{
    private static final Logger logger = LogManager.getLogger(GetAnyEventsFromPosition.class);
    private final StreamV3Api streamV3Api;
    private final String streamId;

    public GetAnyEventsFromPosition(StreamV3Api streamV3Api, String streamId)
    {
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    @Override
    public List<String> execute(String position) throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("getting events in stream {0} from position {1}", streamId, position));
        }

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.getStreamEventsFromPosition(streamId, position);
        }, streamV3Api, logger, "getAnyEventsFromPosition", NetworkExecutor.HTTP_RESPONSE_200);

        //for now just grab the id (id=streamPosition)
        JSONArray resultsJsonArray = future.get().getBody().getArray();
        List<String> results = new ArrayList<>(resultsJsonArray.length());
        for (int i=0; i<resultsJsonArray.length(); i++) {
            JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
            results.add(jsonObject.getString("id")); //another key to add later: source
        }

        return results;
    }
}
