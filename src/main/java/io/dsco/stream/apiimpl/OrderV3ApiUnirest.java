package io.dsco.stream.apiimpl;

import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.domain.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getOrder(@NotNull GetOrderRequest getOrderRequest)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("orderKey", getOrderRequest.getOrderKey());
        params.put("value", getOrderRequest.getValue());
        if (getOrderRequest.getDscoAccountId() != null) {
            params.put("dscoAccountId", getOrderRequest.getDscoAccountId());
        }
        if (getOrderRequest.getDscoTradingPartnerId() != null) {
            params.put("dscoTradingPartnerId", getOrderRequest.getDscoTradingPartnerId());
        }
        return Unirest.get(baseUrl + "order")
                .headers(defaultHeaders)
                .queryString(params)
                .queryString("_ts", System.currentTimeMillis()) //to prevent 404 caching
                .asJsonAsync();
    }
}
