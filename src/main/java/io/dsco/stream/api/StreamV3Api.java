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
    enum ObjectType { order, invoice, inventory, catalog, catalogchangelog, orderitemchange, undeliverableshipment }

    enum OperationType { sync, setpartitionowner }

    CompletableFuture<HttpResponse<JsonNode>> createStream(
            @NotNull String id, @NotNull String description, int numPartitions,
            @NotNull ObjectType objectType, @Nullable Map<String, Object> query);

    CompletableFuture<HttpResponse<JsonNode>> listStreams(String id, Integer partitionId);

    //TODO: this should be modified to take a Stream object
    CompletableFuture<HttpResponse<JsonNode>> updateStream(
            @NotNull String id, String description, int numPartitions, boolean incrementVersionNumber,
            @Nullable Map<String, Object> query);

    CompletableFuture<HttpResponse<JsonNode>> createStreamOperation(
            @NotNull String id, @NotNull OperationType operationType, Integer partitionId, String ownerId);

    CompletableFuture<HttpResponse<JsonNode>> getStreamEventsFromPosition(
            @NotNull String id, int partitionId, @NotNull String position);

    CompletableFuture<HttpResponse<JsonNode>> getStreamEventsInRange(
            @NotNull String id, int partitionId, @NotNull String startPosition, @NotNull String endPosition);

    CompletableFuture<HttpResponse<JsonNode>> updateStreamPosition(
            @NotNull String id, int partitionId, @NotNull String position);

    CompletableFuture<HttpResponse<JsonNode>> deleteStream(@NotNull String id);
}
