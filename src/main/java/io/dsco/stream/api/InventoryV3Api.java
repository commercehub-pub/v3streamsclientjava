package io.dsco.stream.api;

import io.dsco.stream.domain.ItemInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InventoryV3Api
{
    CompletableFuture<HttpResponse<JsonNode>> updateInventorySmallBatch(@NotNull List<ItemInventory> items);

    CompletableFuture<HttpResponse<JsonNode>> getInventoryChangeLog(@NotNull String requestId);
}
