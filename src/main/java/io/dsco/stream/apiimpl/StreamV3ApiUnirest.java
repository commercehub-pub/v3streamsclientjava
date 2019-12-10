package io.dsco.stream.apiimpl;

import com.google.gson.Gson;
import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class StreamV3ApiUnirest
extends BaseApiUnirest
implements StreamV3Api
{
    StreamV3ApiUnirest(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        super(clientId, secret, baseUrl);
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createStream(
            @NotNull String id, @NotNull String description, int numPartitions, @NotNull ObjectType objectType, Map<String, Object> query)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("description", description);
        body.put("numPartitions", numPartitions);
        body.put("objectType", objectType.toString());

        if (query != null) {
            //ensure that query contains the required value AND that it matches the objectType
            ObjectType queryType = (ObjectType) query.get("queryType");
            if (queryType == null) throw new IllegalArgumentException("queryType not found in query");
            if (queryType != objectType) throw new IllegalArgumentException("queryType does not match objectType");
            body.put("query", query);
        }

        return Unirest.post(baseUrl + "stream")
                .headers(defaultHeaders)
                .body(body)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> listStreams(String id, List<Integer> partitionIds)
    {
        //note: i am adding the _ts param because if this is first called and gets a 404
        // that value seems to be cached inside of Unirest, and even if later on the url
        // does exist, it fails to find it because of the caching. but by putting the _ts
        // with a constantly updating value, it forces it to not cache.

        //another potential option: Cache-Control: no-cache

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("_ts", System.currentTimeMillis());
        if (id != null) {
            queryParams.put("id", id);
        }
        if (partitionIds != null && partitionIds.size() > 0) {
            queryParams.put("partitionIds", partitionIds);
        }

        return Unirest.get(baseUrl + "stream")
                .headers(defaultHeaders)
                .queryString(queryParams)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> updateStream(
            @NotNull String id, String description, int numPartitions, boolean incrementVersionNumber, Map<String, Object> query)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("id", id);
        if (description != null) {
            body.put("description", description);
        }
        if (query != null) {
            body.put("objectType", query.get("queryType"));
            body.put("query", query);
        }
        body.put("numPartitions", numPartitions);
        body.put("incrementVersionNumber", incrementVersionNumber);

        return Unirest.put(baseUrl + "stream/{id}")
                .routeParam("id", id)
                .headers(defaultHeaders)
                .body(body)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createStreamOperation(@NotNull String id, @NotNull OperationType operationType)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("operationType", operationType.toString());

        return Unirest.post(baseUrl + "stream/{id}")
                .routeParam("id", id)
                .headers(defaultHeaders)
                .body(body)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getStreamEventsFromPosition(
            @NotNull String id, int partitionId, @NotNull String position)
    {
        return Unirest.get(baseUrl + "stream/{id}/{partitionId}/{position}")
                .routeParam("id", id)
                .routeParam("partitionId", partitionId+"")
                .routeParam("position", position)
                .headers(defaultHeaders)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getStreamEventsInRange(
            @NotNull String id, int partitionId, @NotNull String startPosition, @NotNull String endPosition)
    {
        return Unirest.get(baseUrl + "stream/{id}/{partitionId}/{startPosition}/{endPosition}")
                .routeParam("id", id)
                .routeParam("partitionId", partitionId+"")
                .routeParam("startPosition", startPosition)
                .routeParam("endPosition", endPosition)
                .headers(defaultHeaders)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> updateStreamPosition(@NotNull String id, int partitionId, @NotNull String position)
    {
        return Unirest.put(baseUrl + "stream/{id}/{partitionId}/{position}")
                .routeParam("id", id)
                .routeParam("partitionId", partitionId+"")
                .routeParam("position", position)
                .headers(defaultHeaders)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> deleteStream(@NotNull String id)
    {
        return Unirest.delete(baseUrl + "stream/{id}")
                .routeParam("id", id)
                .headers(defaultHeaders)
                .asJsonAsync();
    }
}
