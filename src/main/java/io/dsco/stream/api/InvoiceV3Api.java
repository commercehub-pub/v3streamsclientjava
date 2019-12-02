package io.dsco.stream.api;

import io.dsco.stream.domain.InvoiceForUpdate;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InvoiceV3Api
{
    public enum ChangeLogStatus {pending, success, failure, success_or_failure}

    CompletableFuture<HttpResponse<JsonNode>> createInvoiceSmallBatch(@NotNull List<InvoiceForUpdate> invoices);

    //none are required. whatever is passed it will be passed along to the API call
    CompletableFuture<HttpResponse<JsonNode>> getInvoiceChangeLog(
            String startDate, String endDate, ChangeLogStatus status, String scrollId);
}
