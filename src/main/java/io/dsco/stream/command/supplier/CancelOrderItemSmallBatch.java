package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.OrderForCancel;
import io.dsco.stream.domain.SyncUpdateResponse;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CancelOrderItemSmallBatch
implements Command<List<OrderForCancel>, SyncUpdateResponse>
{
    private static final Logger logger = LogManager.getLogger(CancelOrderItemSmallBatch.class);
    private final OrderV3Api orderV3Api;

    public CancelOrderItemSmallBatch(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public SyncUpdateResponse execute(List<OrderForCancel> orderItemsToCancel) throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
logger.info(Util.gson().toJson(orderItemsToCancel));
            return orderV3Api.cancelOrderItemsSmallBatch(orderItemsToCancel);
        }, orderV3Api, logger, "cancelOrderItemsSmallBatch", NetworkExecutor.HTTP_RESPONSE_202);

logger.info(future.get().getBody());
        return Util.gson().fromJson(future.get().getBody().toString(), SyncUpdateResponse.class);
    }
}
