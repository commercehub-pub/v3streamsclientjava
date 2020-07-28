package io.dsco.stream.command.supplier;

import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.GetOrderById;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class GetOrder
implements Command<GetOrderById, Integer>
{
    private static final Logger logger = LogManager.getLogger(GetOrder.class);

    private final OrderV3Api orderV3Api;

    public GetOrder(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public Integer execute(GetOrderById orderRequest)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return orderV3Api.getOrder(orderRequest);
        }, orderV3Api, logger, "getOrder", NetworkExecutor.HTTP_RESPONSE_200or404);

logger.info(future.get().getBody());
        return future.get().getStatus();
    }
}
