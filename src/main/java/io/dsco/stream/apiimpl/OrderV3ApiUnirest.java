package io.dsco.stream.apiimpl;

import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.domain.Order;
import io.dsco.stream.domain.OrderAcknowledge;
import io.dsco.stream.domain.OrderCancelItem;
import io.dsco.stream.domain.OrderShipment;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OrderV3ApiUnirest
extends BaseApiUnirest
implements OrderV3Api
{
    public OrderV3ApiUnirest(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        super(clientId, secret, baseUrl);
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createOrder(@NotNull Order order)
    {
        return Unirest.post(baseUrl + "order")
                .headers(defaultHeaders)
                .body(order)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> acknowledgeOrders(@NotNull List<OrderAcknowledge> ordersToAcknowledge)
    {
        return Unirest.post(baseUrl + "order/acknowledge")
                .headers(defaultHeaders)
                .body(ordersToAcknowledge)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> cancelOrderItemsSmallBatch(@NotNull List<OrderCancelItem> orderItemsToCancel)
    {
        return Unirest.post(baseUrl + "order/item/cancel/batch/small")
                .headers(defaultHeaders)
                .body(orderItemsToCancel)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createShipmentSmallBatch(@NotNull List<OrderShipment> orderShipments)
    {
        return Unirest.post(baseUrl + "order/shipment/batch/small")
                .headers(defaultHeaders)
                .body(orderShipments)
                .asJsonAsync();
    }
}
