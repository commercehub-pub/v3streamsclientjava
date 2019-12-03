package io.dsco.stream.shared;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.OAuthSupport;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NetworkExecutor
{
    public static Set<Integer> HTTP_RESPONSE_200 = Collections.singleton(200);
    public static Set<Integer> HTTP_RESPONSE_201 = Collections.singleton(201);
    public static Set<Integer> HTTP_RESPONSE_202 = Collections.singleton(202);
    public static Set<Integer> HTTP_RESPONSE_200or404 = Stream.of(200, 404).collect(Collectors.toSet());

    private String authEndpoint;

    //NOTE: for now, using the singleton pattern.
    // Later on, use a factory pattern so that a limited number can be created and spread amongst various threads
    private static NetworkExecutor instance;
    public static NetworkExecutor getInstance()
    {
        if (instance == null) {
            instance=  new NetworkExecutor();
        }
        return instance;
    }

    private NetworkExecutor() {}

    public void setAuthEndpoint(String authEndpoint)
    {
        this.authEndpoint = authEndpoint;
    }

    public CompletableFuture<HttpResponse<JsonNode>> execute(
            @NotNull CheckedFunction<Object, CompletableFuture<HttpResponse<JsonNode>>> f,
            @NotNull OAuthSupport oAuthSupport,
            @NotNull Logger logger, @NotNull String logDescription,
            @NotNull Set<Integer> validResponseCodes)
    throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("about to execute network call: {0}", logDescription));
        }

        int numRetries = 3;
        while (numRetries > 0) {

            if (System.currentTimeMillis() + 30_000L > oAuthSupport.getExpiresAt()) {
                //token either has expired or will soon expire; get another one
                doTokenRefresh(oAuthSupport, logger);
            }

            //execute the network call
            CompletableFuture<HttpResponse<JsonNode>> result = f.apply(null);

            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("waiting for response for: {0}", logDescription));
            }

            int httpStatus = result.get().getStatus();
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("http response code for: {0}: {1}", logDescription, httpStatus));
            }

            if (httpStatus == 401) {
                //401 means the auth token was invalidated; this will log that fact, and refresh the token
                numRetries--;
logger.info(result.get().getBody());
                if (logger.isDebugEnabled()) {
                    logger.debug("auth token not valid. refreshing and retrying...");
                }
                doTokenRefresh(oAuthSupport, logger);

            } else if (!validResponseCodes.contains(httpStatus)) {
                //if the response code is invalid, throw an error
                throw new IllegalStateException(MessageFormat.format(
                        "got invalid http response when checking {0}. got: {1}, expected: {2}",
                        logDescription, httpStatus, validResponseCodes));

            } else {
                //all is well; pass the result back to the caller
                return result;
            }
        }

        //if execution gets here, it means the code kept getting 401 (not authorized), and ran out of retries
        throw new IllegalStateException(MessageFormat.format(
                "got invalid http response when checking {0}. got: {1}, expected: {2}",
                logDescription, 401, validResponseCodes));
    }

    private void doTokenRefresh(OAuthSupport oAuthSupport, Logger logger)
    throws Exception
    {
        if (authEndpoint == null) {
            throw new IllegalStateException("setAuthEndpoint never called on NetworkExecutor");
        }

        CompletableFuture<HttpResponse<JsonNode>> future = Unirest.post(authEndpoint)
                //.header("Content-Type", "application/x-www-form-urlencoded") -- this is defaulted when using the .field calls
                .field("client_id", URLEncoder.encode(oAuthSupport.getClientId(), "UTF-8"))
                .field("client_secret", URLEncoder.encode(oAuthSupport.getSecret(), "UTF-8"))
                .asJsonAsync();

        int httpStatus = future.get().getStatus();
        if (httpStatus == 200) {
            OAuth2Response oAuth2Response = new Gson().fromJson(future.get().getBody().toString(), OAuth2Response.class);
            long expiresAt = Util.iso8601ToDate(oAuth2Response.expiration).getTime();

            oAuthSupport.setTokenAndExpiration(oAuth2Response.token, expiresAt);

        } else {
            logger.error("unexpected response from oAuth:\n" + future.get().getBody());
        }
    }

    private static class OAuth2Response
    {
        public String token;
        public String expiration;
    }
}
