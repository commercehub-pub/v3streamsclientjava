package io.dsco.stream.api;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public interface InventoryV2Api
extends OAuthSupport
{
    CompletableFuture<HttpResponse<JsonNode>> getAllInventory(@Nullable String pageIdentifier, @Nullable String updatedSince);
}
