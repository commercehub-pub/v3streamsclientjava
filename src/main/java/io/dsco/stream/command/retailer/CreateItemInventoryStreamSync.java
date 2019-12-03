package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class CreateItemInventoryStreamSync
implements Command<Void, String>
{
    private static final Logger logger = LogManager.getLogger(CreateItemInventoryStreamSync.class);
    private final StreamV3Api streamV3Api;
    private final java.lang.String streamId;

    public CreateItemInventoryStreamSync(StreamV3Api streamV3Api, java.lang.String streamId)
    {
        this.streamId = streamId;
        this.streamV3Api = streamV3Api;
    }

    @Override
    public String execute(Void x) throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug("creating stream sync operation");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.createStreamOperation(streamId, StreamV3Api.OperationType.sync);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for sync operation");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for sync operation: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            throw new IllegalStateException("got invalid http response when creating sync operation: " + httpStatus);
        }

        //return the operationUuid
//logger.info(future.get().getBody());
        return future.get().getBody().getObject().getString("operationUuid");
    }
}
