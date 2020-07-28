package io.dsco.stream.apiimpl;

import com.google.gson.JsonObject;
import io.dsco.demo.Util;
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
    public CompletableFuture<HttpResponse<JsonNode>> acknowledgeOrders(@NotNull List<OrderId> ordersToAcknowledge)
    {
        return Unirest.post(baseUrl + "order/acknowledge")
                .headers(defaultHeaders)
                .body(ordersToAcknowledge)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> cancelOrderItemsSmallBatch(@NotNull List<OrderForCancel> orderItemsToCancel)
    {
        return Unirest.post(baseUrl + "order/item/cancel/batch/small")
                .headers(defaultHeaders)
                .body(orderItemsToCancel)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createShipmentSmallBatch(@NotNull List<ShipmentsForUpdate> orderShipments)
    {
        return Unirest.post(baseUrl + "order/shipment/batch/small")
                .headers(defaultHeaders)
                .body(orderShipments)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getOrder(@NotNull GetOrderById getOrderRequest)
    {
        //the order key is an enum value that differs (potentially) from the actual enum name; we must be the actual value
        // that should be passed. there are 2 ways (i can think of) to do this: use reflection, or use the gson serialization

        //convert from a java object to a json string and then into a json dom and then grab the serialized version of the orderKey
        JsonObject jsonObject = Util.gson().fromJson(Util.gson().toJson(getOrderRequest), JsonObject.class);
        String orderKeySerializedName = jsonObject.get("orderKey").getAsString();

        Map<String, Object> params = new HashMap<>();
        params.put("orderKey", orderKeySerializedName);
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
