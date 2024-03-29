package io.dsco.stream.api;

public interface OAuthSupport
{
    String getClientId();
    String getSecret();
    void setTokenAndExpiration(String token, long expiresIn);
    long getExpiresAt();
}
