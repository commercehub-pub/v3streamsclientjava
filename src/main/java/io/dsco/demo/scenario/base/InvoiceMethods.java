package io.dsco.demo.scenario.base;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.*;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//TODO: move this down into a set of invoice commands
public class InvoiceMethods
{
    private final StreamV3Api streamV3ApiRetailer;
    private final String streamId;
    private final Logger logger;

    //retailer stream and streamId are passed here because the are used in the lambda function and can't be
    // passed as params later
    public InvoiceMethods(@NotNull StreamV3Api streamV3ApiRetailer, @NotNull String streamId, @NotNull Logger logger)
    {
        this.streamV3ApiRetailer = streamV3ApiRetailer;
        this.streamId = streamId;
        this.logger = logger;
    }

    public ResponseInvoiceCreate createInvoiceSmallBatch(InvoiceV3Api invoiceV3ApiSupplier, List<InvoiceForUpdate> invoices)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return invoiceV3ApiSupplier.createInvoiceSmallBatch(invoices);
        }, invoiceV3ApiSupplier, logger, "createInvoiceSmallBatch", NetworkExecutor.HTTP_RESPONSE_202);
//logger.info(future.get().getBody());

        return new Gson().fromJson(future.get().getBody().toString(), ResponseInvoiceCreate.class);
    }

    /**
     * find the given invoice update request and return it
     */
    public InvoiceChangeLog getInvoiceChangeLog(InvoiceV3Api invoiceV3ApiSupplier, String requestId, String eventDate)
    throws Exception
    {
        //due to propagation delay issues, recommended best practice is to shift everything back by 10 seconds and begin searching from then
        ZonedDateTime from = ZonedDateTime.parse(eventDate).minusSeconds(10L);
        String fromDateMinus10Seconds = Util.dateToIso8601(Date.from(from.toInstant()));

        String nowMinus10Seconds = Util.dateToIso8601(new Date(System.currentTimeMillis()-10_000L));

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return invoiceV3ApiSupplier.getInvoiceChangeLog(fromDateMinus10Seconds, nowMinus10Seconds, null, null);
        }, invoiceV3ApiSupplier, logger, "getInvoiceChangeLog", NetworkExecutor.HTTP_RESPONSE_200);

//logger.info(future.get().getBody());

        //convert to java and look for the given requestId
        InvoiceChangeLog requestedResponseRecord = null;
        ResponseInvoiceChangeLog response = new Gson().fromJson(future.get().getBody().toString(), ResponseInvoiceChangeLog.class);
        for (InvoiceChangeLog log : response.getLogs()) {
            if (log.getRequestId().equals(requestId)) {
                requestedResponseRecord = log;
                break;
            }
        }

        return requestedResponseRecord;
    }

    public List<StreamItemInvoice> getInvoiceEventsFromPosition(String position)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3ApiRetailer.getStreamEventsFromPosition(streamId, position);
        }, streamV3ApiRetailer, logger, "getInvoiceEventsFromPosition", NetworkExecutor.HTTP_RESPONSE_200);

        JSONArray list = future.get().getBody().getArray();

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
        }

        StreamItemInvoice[] results = new Gson().fromJson(future.get().getBody().toString(), StreamItemInvoice[].class);
        return Arrays.asList(results);
    }
}
