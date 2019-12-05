package io.dsco.stream.apiimpl;

import com.google.gson.Gson;
import io.dsco.stream.api.StreamV3Api;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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
            @NotNull String id, @NotNull String description, ObjectType objectType, Map<String, Object> query)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("description", description);
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
    public CompletableFuture<HttpResponse<JsonNode>> listStream(@NotNull String id)
    {
        //note: i am adding the _ts param because if this is first called and gets a 404
        // that value seems to be cached inside of Unirest, and even if later on the url
        // does exist, it fails to find it because of the caching. but by putting the _ts
        // with a constantly updating value, it forces it to not cache.

        return Unirest.get(baseUrl + "stream")
                .headers(defaultHeaders)
                .queryString("id", id)
                .queryString("_ts", System.currentTimeMillis())
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> listStreams()
    {
        return Unirest.get(baseUrl + "stream")
                .headers(defaultHeaders)
                .asJsonAsync();
    }

//    @Override
//    public CompletableFuture<HttpResponse<JsonNode>> updateStreamDescription(@NotNull String id, @NotNull String newDescription, Map<String, Object> query)
//    {
//        Map<String, Object> body = new HashMap<>();
//        body.put("id", id);
//        body.put("description", newDescription);
//        body.put("objectType", "order"); //no...
//        body.put("query", query);
//
//        return Unirest.put(baseUrl + "stream/{id}")
//                .routeParam("id", id)
//                .headers(defaultHeaders)
//                .body(body)
//                .asJsonAsync();
//    }

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
    public CompletableFuture<HttpResponse<JsonNode>> getStreamEventsFromPosition(@NotNull String id, @NotNull String position)
    {
        return Unirest.get(baseUrl + "stream/{id}/{position}")
                .routeParam("id", id)
                .routeParam("position", position)
                .headers(defaultHeaders)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getStreamEventsInRange(@NotNull String id, @NotNull String startPosition, @NotNull String endPosition)
    {
        return Unirest.get(baseUrl + "stream/{id}/{startPosition}/{endPosition}")
                .routeParam("id", id)
                .routeParam("startPosition", startPosition)
                .routeParam("endPosition", endPosition)
                .headers(defaultHeaders)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> updateStreamPosition(@NotNull String id, @NotNull String position)
    {
        return Unirest.put(baseUrl + "stream/{id}/{position}")
                .routeParam("id", id)
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
