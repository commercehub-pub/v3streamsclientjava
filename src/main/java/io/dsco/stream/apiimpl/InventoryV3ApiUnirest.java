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

        //TODO: workaround to not use oauth on inventory stream until monty fixes it
        setTokenAndExpiration("478468df-019d-40d9-a9b4-d0b2503ec35b", 999_999_999L);
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
