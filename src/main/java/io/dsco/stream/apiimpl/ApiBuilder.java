package io.dsco.stream.apiimpl;

import io.dsco.stream.api.*;
import org.jetbrains.annotations.NotNull;

public class ApiBuilder
{
    public static StreamV3Api getStreamV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new StreamV3ApiUnirest(clientId, secret, baseUrl);
    }

    public static InventoryV3Api getInventoryV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new InventoryV3ApiUnirest(clientId, secret, baseUrl);
    }

    public static InvoiceV3Api getInvoiceV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new InvoiceV3ApiUnirest(clientId, secret, baseUrl);
    }

    public static OrderV3Api getOrderV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new OrderV3ApiUnirest(clientId, secret, baseUrl);
    }

}
