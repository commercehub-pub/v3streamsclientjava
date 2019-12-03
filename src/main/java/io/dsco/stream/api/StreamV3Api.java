package io.dsco.stream.api;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface StreamV3Api
extends OAuthSupport
{
    enum ObjectType {order, invoice, inventory, catalog, catalogchangelog, orderitemchange, undeliverableshipment}

    enum OperationType {sync}

    CompletableFuture<HttpResponse<JsonNode>> createStream(
            @NotNull String id, @NotNull String description, @NotNull ObjectType objectType, @Nullable Map<String, Object> query);

    CompletableFuture<HttpResponse<JsonNode>> listStream(@NotNull String id);

    CompletableFuture<HttpResponse<JsonNode>> listStreams();

    CompletableFuture<HttpResponse<JsonNode>> updateStreamDescription(
            @NotNull String id, @NotNull String newDescription, @Nullable Map<String, Object> query);

    CompletableFuture<HttpResponse<JsonNode>> createStreamOperation(@NotNull String id, @NotNull OperationType operationType);

    CompletableFuture<HttpResponse<JsonNode>> getStreamEventsFromPosition(@NotNull String id, @NotNull String position);

    CompletableFuture<HttpResponse<JsonNode>> getStreamEventsInRange(
            @NotNull String id, @NotNull String startPosition, @NotNull String endPosition);

    CompletableFuture<HttpResponse<JsonNode>> updateStreamPosition(@NotNull String id, @NotNull String position);

    CompletableFuture<HttpResponse<JsonNode>> deleteStream(@NotNull String id);
}
