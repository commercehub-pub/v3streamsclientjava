package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamEventInventory;
import io.dsco.stream.shared.NetworkExecutor;
import io.dsco.stream.shared.StreamItemInventoryBase;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetItemInventoryEventsFromPosition
extends StreamItemInventoryBase
{
    private static final Logger logger = LogManager.getLogger(GetItemInventoryEventsFromPosition.class);
    private final StreamV3Api streamV3Api;
    private final String streamId;

    public GetItemInventoryEventsFromPosition(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        super(uniqueIdentifierKey, logger);

        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    @Override
    public List<StreamEventInventory> execute(List<String> positions) throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("getting events in stream {0} from position {1}", streamId, positions.get(0)));
        }

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.getStreamEventsFromPosition(streamId, 0, positions.get(0));
        }, streamV3Api, logger, "getItemInventoryEventsFromPosition", NetworkExecutor.HTTP_RESPONSE_200);

        return refactorParseStreamEvents(future);
    }
}
