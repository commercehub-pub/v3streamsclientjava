package io.dsco.stream.apiimpl;

import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.domain.ItemInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InventoryV3ApiUnirest
extends BaseApiUnirest
implements InventoryV3Api
{
    InventoryV3ApiUnirest(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        super(clientId, secret, baseUrl);
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> updateInventorySmallBatch(@NotNull List<ItemInventory> items)
    {
        return Unirest.post(baseUrl + "inventory/batch/small")
                .headers(defaultHeaders)
                .body(items)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getInventoryChangeLog(@NotNull String requestId)
    {
        return Unirest.get(baseUrl + "inventory/log")
                .headers(defaultHeaders)
                .queryString("requestId", requestId)
                .asJsonAsync();
    }
}
