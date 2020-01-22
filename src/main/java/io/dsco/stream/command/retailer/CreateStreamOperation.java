package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class CreateStreamOperation
implements Command<Void, String>
{
    private static final Logger logger = LogManager.getLogger(CreateStreamOperation.class);
    private final StreamV3Api streamV3Api;
    private final String streamId;
    public final StreamV3Api.OperationType operationType;
    public final Integer partitionId;
    public final String ownerId;

    public CreateStreamOperation(StreamV3Api streamV3Api, String streamId, StreamV3Api.OperationType operationType, Integer partitionId, String ownerId)
    {
        this.streamId = streamId;
        this.streamV3Api = streamV3Api;
        this.operationType = operationType;
        this.partitionId = partitionId;
        this.ownerId = ownerId;
    }

    @Override
    public String execute(Void v) throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.createStreamOperation(streamId, operationType, partitionId, ownerId);
        }, streamV3Api, logger, "createStreamOperation", NetworkExecutor.HTTP_RESPONSE_200);

        //return the operationUuid
//logger.info(future.get().getBody());
        return future.get().getBody().getObject().getString("operationUuid");
    }
}
