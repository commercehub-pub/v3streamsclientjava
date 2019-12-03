package io.dsco.stream.apiimpl;

import io.dsco.stream.api.OAuthSupport;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseApiUnirest
implements OAuthSupport
{
    protected final String baseUrl;
    private final String clientId;
    private final String secret;
    protected final Map<String, String> defaultHeaders;
    private long expiresAt;

    BaseApiUnirest(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        this.clientId = clientId;
        this.secret = secret;
        this.baseUrl = baseUrl;

        defaultHeaders = new HashMap<>();
        defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("Authorization", "bearer xxx"); //placeholder
    }

    @Override
    public String getClientId()
    {
        return clientId;
    }

    @Override
    public String getSecret()
    {
        return secret;
    }

    @Override
    public void setTokenAndExpiration(String token, long expiresAt /*int expiresIn*/)
    {
        //store the new auth token in the authorization header
        defaultHeaders.put("Authorization", "bearer " + token);

        //determine when the token will expire
        //expiresIn is in seconds. convert to milliseconds, add to the current time, and we now have expiresAt
        //expiresAt = System.currentTimeMillis() + expiresIn * 1_000L;

        this.expiresAt = expiresAt;
    }

    @Override
    public long getExpiresAt()
    {
        return expiresAt;
    }
}
