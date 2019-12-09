package io.dsco.stream.command.retailer;

import com.google.gson.Gson;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.CreateOrderResponse;
import io.dsco.stream.domain.Order;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateOrder
implements Command<Order, CreateOrderResponse>
{
    private static final Logger logger = LogManager.getLogger(CreateOrder.class);
    private final OrderV3Api orderV3Api;

    public CreateOrder(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public CreateOrderResponse execute(Order order)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
//logger.info("create order REQUEST\n" + new Gson().toJson(order));
            return orderV3Api.createOrder(order);
        }, orderV3Api, logger, "createOrder", NetworkExecutor.HTTP_RESPONSE_201);
//logger.info("create order RESPONSE:\n" + future.get().getBody());

        return new Gson().fromJson(future.get().getBody().toString(), CreateOrderResponse.class);
    }
}
