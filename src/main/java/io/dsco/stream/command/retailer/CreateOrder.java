package io.dsco.stream.command.retailer;

import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.Order;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class CreateOrder
implements Command<Order, String>
{
    private static final Logger logger = LogManager.getLogger(CreateOrder.class);
    private final OrderV3Api orderV3Api;

    public CreateOrder(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public String execute(Order order)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
            return orderV3Api.createOrder(order);
        }, orderV3Api, logger, "createOrder", NetworkExecutor.HTTP_RESPONSE_201);

        return future.get().getBody().getObject().getString("status");
    }
}
