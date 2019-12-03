package io.dsco.stream.apiimpl;

import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.api.InventoryV2Api;
import org.jetbrains.annotations.NotNull;

public class ApiBuilder
{
    public static StreamV3Api getStreamV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new StreamV3ApiUnirest(clientId, secret, baseUrl);
    }

    public static InventoryV2Api getInventoryV2Api(String accessToken, String baseUrl)
    {
        return new InventoryV2ApiUnirest(accessToken, baseUrl);
    }

    public static InventoryV3Api getInventoryV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new InventoryV3ApiUnirest(clientId, secret, baseUrl);
    }

    public static InvoiceV3Api getInvoiceV3Api(@NotNull String clientId, @NotNull String secret, @NotNull String baseUrl)
    {
        return new InvoiceV3ApiUnirest(clientId, secret, baseUrl);
    }
}
