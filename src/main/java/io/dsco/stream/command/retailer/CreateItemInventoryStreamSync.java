package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class CreateItemInventoryStreamSync
implements Command<Void, String>
{
    private static final Logger logger = LogManager.getLogger(CreateItemInventoryStreamSync.class);
    private final StreamV3Api streamV3Api;
    private final java.lang.String streamId;

    public CreateItemInventoryStreamSync(StreamV3Api streamV3Api, String streamId)
    {
        this.streamId = streamId;
        this.streamV3Api = streamV3Api;
    }

    @Override
    public String execute(Void v) throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.createStreamOperation(streamId, StreamV3Api.OperationType.sync, null, null);
        }, streamV3Api, logger, "createStreamSync", NetworkExecutor.HTTP_RESPONSE_200);

        //return the operationUuid
//logger.info(future.get().getBody());
        return future.get().getBody().getObject().getString("operationUuid");
    }
}
