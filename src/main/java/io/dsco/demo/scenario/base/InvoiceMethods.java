package io.dsco.demo.scenario.base;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.*;
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
import java.util.concurrent.ExecutionException;

//TODO: can probably be moved to an interface now for the invoice specific methods
/**
 * needed to be a class rather than an interface so the getInvoiceEventsFromPosition can be passed to a lambda function
 */
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
    throws ExecutionException, InterruptedException
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("About to add {0} invoices", invoices.size()));
        }

        CompletableFuture<HttpResponse<JsonNode>> future = invoiceV3ApiSupplier.createInvoiceSmallBatch(invoices);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for invoices add");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for invoice add: {0}", httpStatus));
        }
        if (httpStatus != 202) {
            throw new IllegalStateException("got invalid http response when adding invoices: " + httpStatus);
        }

//logger.info(future.get().getBody());

        return new Gson().fromJson(future.get().getBody().toString(), ResponseInvoiceCreate.class);
    }

    /**
     * find the given invoice update request and return it
     */
    public InvoiceChangeLog getInvoiceChangeLog(InvoiceV3Api invoiceV3ApiSupplier, String requestId, String eventDate)
    throws ExecutionException, InterruptedException
    {
        //due to propagation delay issues, recommended best practice is to shift everything back by 10 seconds and begin searching from then
        ZonedDateTime from = ZonedDateTime.parse(eventDate).minusSeconds(10L);
        String fromDateMinus10Seconds = Util.dateToIso8601(Date.from(from.toInstant()));

        String nowMinus10Seconds = Util.dateToIso8601(new Date(System.currentTimeMillis()-10_000L));

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "About to check status of added invoice, beginEnd: {0}, endDate: {1} (now={2})", fromDateMinus10Seconds, nowMinus10Seconds, Util.dateToIso8601(new Date())));
        }

        CompletableFuture<HttpResponse<JsonNode>> future =
                invoiceV3ApiSupplier.getInvoiceChangeLog(fromDateMinus10Seconds, nowMinus10Seconds, null, null);

        if (logger.isDebugEnabled()) {
            logger.debug("waiting for response for status check");
        }

        int httpStatus = future.get().getStatus();
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("http response code for status check: {0}", httpStatus));
        }
        if (httpStatus != 200) {
            logger.error(future.get().getBody());
            throw new IllegalStateException("got invalid http response when checking status: " + httpStatus);
        }

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

    //can't throw an exception. used in lambda function call
    public List<StreamItemInvoice> getInvoiceEventsFromPosition(String position)
    {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("getting invoice events in stream {0} from position {1}", streamId, position));
            }

            CompletableFuture<HttpResponse<JsonNode>> future = streamV3ApiRetailer.getStreamEventsFromPosition(streamId, position);

            if (logger.isDebugEnabled()) {
                logger.debug("waiting for response for stream events");
            }

            int httpStatus = future.get().getStatus();
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("http response code for stream events: {0}", httpStatus));
            }
            if (httpStatus != 200) {
                throw new IllegalStateException("got invalid http response when retrieving stream events: " + httpStatus);
            }

            JSONArray list = future.get().getBody().getArray();

            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
            }

            StreamItemInvoice[] results = new Gson().fromJson(future.get().getBody().toString(), StreamItemInvoice[].class);
            return Arrays.asList(results);

        } catch (Exception e) {
            logger.error("unexpected error", e);
            return null;
        }
    }
}
