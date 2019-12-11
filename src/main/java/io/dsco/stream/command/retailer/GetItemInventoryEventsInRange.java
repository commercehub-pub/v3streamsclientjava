package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
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

public class GetItemInventoryEventsInRange
extends StreamItemInventoryBase
implements Command<List<String>, List<StreamEventInventory>>
{
    private static final Logger logger = LogManager.getLogger(GetItemInventoryEventsInRange.class);
    private final StreamV3Api streamV3Api;
    private final String streamId;

    public GetItemInventoryEventsInRange(StreamV3Api streamV3Api, String streamId, String uniqueIdentifierKey)
    {
        super(uniqueIdentifierKey, logger);

        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
    }

    @Override
    public List<StreamEventInventory> execute(List<String> positions) throws Exception
    {
        String startPosition = positions.get(0);
        String endPosition = positions.get(1);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "getting events in stream {0} from {1} to {2}",
                    streamId, startPosition, endPosition));
        }

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.getStreamEventsInRange(streamId, 0, startPosition, endPosition);
        }, streamV3Api, logger, "getItemInventoryEventsInRange", NetworkExecutor.HTTP_RESPONSE_200);

        return refactorParseStreamEvents(future);
    }

}
