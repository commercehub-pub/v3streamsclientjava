package io.dsco.stream.impl;

import io.dsco.stream.api.InventoryV2Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class InventoryV2ApiUnirest
extends BaseApiUnirest
implements InventoryV2Api
{
    InventoryV2ApiUnirest(@NotNull String accessToken, @NotNull String baseUrl)
    {
        super(accessToken, baseUrl);
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getAllInventory(String pageIdentifier, String updatedSince)
    {
        Map<String, Object> queryParams = new HashMap<>();
        if (pageIdentifier != null) {
            queryParams.put("pageIdentifier", pageIdentifier);
        }
        if (updatedSince != null) {
            queryParams.put("updatedSince", updatedSince);
        }

        return Unirest.get(baseUrl + "iteminventory/all")
                .headers(defaultHeaders)
                .queryString(queryParams)
                .asJsonAsync();

    }
}
