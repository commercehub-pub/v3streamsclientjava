package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.command.retailer.CreateOrder;
import io.dsco.stream.domain.OrderAcknowledge;
import io.dsco.stream.domain.OrderAcknowledgeResponse;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AcknowledgeOrder
implements Command<List<OrderAcknowledge>, OrderAcknowledgeResponse>
{
    private static final Logger logger = LogManager.getLogger(CreateOrder.class);
    private final OrderV3Api orderV3Api;

    public AcknowledgeOrder(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public OrderAcknowledgeResponse execute(List<OrderAcknowledge> ordersToAcknowledge)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future = NetworkExecutor.getInstance().execute((x) -> {
logger.info(new Gson().toJson(ordersToAcknowledge));
            return orderV3Api.acknowledgeOrders(ordersToAcknowledge);
        }, orderV3Api, logger, "acknowledgeOrders", NetworkExecutor.HTTP_RESPONSE_202);

logger.info(future.get().getBody());
        return new Gson().fromJson(future.get().getBody().toString(), OrderAcknowledgeResponse.class);
    }
}
