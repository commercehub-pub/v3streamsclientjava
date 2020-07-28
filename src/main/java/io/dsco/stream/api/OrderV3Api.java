package io.dsco.stream.api;

import io.dsco.stream.domain.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderV3Api
extends OAuthSupport
{
    CompletableFuture<HttpResponse<JsonNode>> createOrder(@NotNull Order order);

    CompletableFuture<HttpResponse<JsonNode>> acknowledgeOrders(@NotNull List<OrderId> ordersToAcknowledge);

    CompletableFuture<HttpResponse<JsonNode>> cancelOrderItemsSmallBatch(@NotNull List<OrderForCancel> orderItemsToCancel);

    CompletableFuture<HttpResponse<JsonNode>> createShipmentSmallBatch(@NotNull List<ShipmentsForUpdate> orderShipments);

    CompletableFuture<HttpResponse<JsonNode>> getOrder(@NotNull GetOrderById getOrderRequest);
}
