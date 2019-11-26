package io.dsco.stream.impl;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseApiUnirest
{
    protected final String baseUrl;
    protected final Map<String, String> defaultHeaders;

    BaseApiUnirest(@NotNull String accessToken, @NotNull String baseUrl)
    {
        this.baseUrl = baseUrl;

        defaultHeaders = new HashMap<>();
        defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("Authorization", "bearer " + accessToken);
    }
}
