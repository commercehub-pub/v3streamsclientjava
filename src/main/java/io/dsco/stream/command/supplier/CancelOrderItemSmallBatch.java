package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.Order;
import io.dsco.stream.domain.OrderCancelItem;
import io.dsco.stream.domain.OrderCancelOrderItemSmallBatch;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CancelOrderItemSmallBatch
implements Command<List<OrderCancelItem>, OrderCancelOrderItemSmallBatch>
{
    private static final Logger logger = LogManager.getLogger(CancelOrderItemSmallBatch.class);
    private final OrderV3Api orderV3Api;

    public CancelOrderItemSmallBatch(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public OrderCancelOrderItemSmallBatch execute(List<OrderCancelItem> orderItemsToCancel) throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
            return orderV3Api.cancelOrderItemsSmallBatch(orderItemsToCancel);
        }, orderV3Api, logger, "cancelOrderItemsSmallBatch", NetworkExecutor.HTTP_RESPONSE_202);

        //return new Gson().fromJson();
        return null;
    }
}
