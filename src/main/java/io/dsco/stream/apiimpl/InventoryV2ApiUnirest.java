package io.dsco.stream.apiimpl;

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
        super(accessToken, "", baseUrl);

        //cause the accessToken to get set
        setTokenAndExpiration(accessToken, 0);
    }

    //for v2, the token never expires, so always return something in the future
    @Override
    public long getExpiresAt()
    {
        return System.currentTimeMillis() + 300_000L;
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
