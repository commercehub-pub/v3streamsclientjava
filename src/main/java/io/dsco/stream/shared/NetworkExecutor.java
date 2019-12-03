package io.dsco.stream.shared;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NetworkExecutor
{
    public static Set<Integer> HTTP_RESPONSE_200 = Collections.singleton(200);

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

    //to create a set with multiple values: Stream.of(200, 404).collect(Collectors.toSet())
    //to create a set with one value: Collections.singleton(200)

    public CompletableFuture<HttpResponse<JsonNode>> execute(
            @NotNull CheckedFunction<Object, CompletableFuture<HttpResponse<JsonNode>>> f,
            @NotNull Logger logger, @NotNull String logDescription,
            @NotNull Set<Integer> validResponseCodes)
    throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("about to execute network call: {0}", logDescription));
        }

        boolean forceTokenRefresh = false;

        while (true) {

            if (forceTokenRefresh) {
                //means we're in a retry loop. refresh the oauth token regardless of whether or not it has expired
                forceTokenRefresh = false;

                //TODO: do a token refresh

            } else {
                //TODO: check to see if token is expired (or about to expire). if so, do a token refresh
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
                //401 means the auth token was invalidated; this will log that fact, and the loop will execute again
                // (which will cause a token refresh)
                if (logger.isDebugEnabled()) {
                    logger.debug("auth token not valid. refreshing and retrying...");
                }
                forceTokenRefresh = true;

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
    }

    private void doTokenRefresh()
    {
        //TODO: do a token refresh
    }
}
