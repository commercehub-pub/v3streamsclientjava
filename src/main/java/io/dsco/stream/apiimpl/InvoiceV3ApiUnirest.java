package io.dsco.stream.apiimpl;

import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.domain.InvoiceForUpdate;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class InvoiceV3ApiUnirest
extends BaseApiUnirest
implements InvoiceV3Api
{
    InvoiceV3ApiUnirest(@NotNull String accessToken, @NotNull String baseUrl)
    {
        super(accessToken, baseUrl);
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> createInvoiceSmallBatch(List<InvoiceForUpdate> invoices)
    {
        return Unirest.post(baseUrl + "invoice/batch/small")
                .headers(defaultHeaders)
                .body(invoices)
                .asJsonAsync();
    }

    @Override
    public CompletableFuture<HttpResponse<JsonNode>> getInvoiceChangeLog(
            String startDate, String endDate, ChangeLogStatus status, String scrollId)
    {
        Map<String, Object> params = new HashMap<>();

        if (scrollId != null) {
            //if this is provided, ignore anything else
            params.put("scrollId", scrollId);

        } else if (startDate != null || endDate != null) {
            //if one is present, both are required
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("both startDate and endDate are required");
            }

            if (startDate != null) params.put("startDate", startDate);
            if (endDate != null) params.put("endDate", endDate);
        }

        if (status != null) params.put("status", status.toString());

        return Unirest.get(baseUrl + "invoice/log")
                .queryString(params)
                .headers(defaultHeaders)
                .asJsonAsync();
    }
}
