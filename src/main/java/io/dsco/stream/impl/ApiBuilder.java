package io.dsco.stream.impl;

import io.dsco.stream.api.InventoryV3Api;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.api.InventoryV2Api;

public class ApiBuilder
{
    public static StreamV3Api getStreamV3Api(String accessToken, String baseUrl)
    {
        return new StreamV3ApiUnirest(accessToken, baseUrl);
    }

    public static InventoryV2Api getInventoryV2Api(String accessToken, String baseUrl)
    {
        return new InventoryV2ApiUnirest(accessToken, baseUrl);
    }

    public static InventoryV3Api getInventoryV3Api(String accessToken, String baseUrl)
    {
        return new InventoryV3ApiUnirest(accessToken, baseUrl);
    }

    public static InvoiceV3Api getInvoiceV3Api(String accessToken, String baseUrl)
    {
        return new InvoiceV3ApiUnirest(accessToken, baseUrl);
    }
}
