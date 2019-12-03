package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamItemInventory;
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
implements Command<List<String>, List<StreamItemInventory>>
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
    public List<StreamItemInventory> execute(List<String> positions) throws Exception
    {
        String startPosition = positions.get(0);
        String endPosition = positions.get(1);

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "getting events in stream {0} from {1} to {2}",
                    streamId, startPosition, endPosition));
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.getStreamEventsInRange(streamId, startPosition, endPosition);

        return refactorParseStreamEvents(future);
    }

}
