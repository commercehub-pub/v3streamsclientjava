package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.command.retailer.CreateOrder;
import io.dsco.stream.domain.InvoiceChangeLog;
import io.dsco.stream.domain.InvoiceForUpdate;
import io.dsco.stream.domain.ResponseInvoiceChangeLog;
import io.dsco.stream.domain.ResponseInvoiceCreate;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateInvoiceSmallBatch
implements Command<List<InvoiceForUpdate>, Void>
{
    private static final Logger logger = LogManager.getLogger(CreateInvoiceSmallBatch.class);

    private final InvoiceV3Api invoiceV3Api;

    public CreateInvoiceSmallBatch(InvoiceV3Api invoiceV3Api)
    {
        this.invoiceV3Api = invoiceV3Api;
    }

    @Override
    public Void execute(List<InvoiceForUpdate> invoices)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return invoiceV3Api.createInvoiceSmallBatch(invoices);
        }, invoiceV3Api, logger, "createInvoiceSmallBatch", NetworkExecutor.HTTP_RESPONSE_202);
//logger.info(future.get().getBody());

        ResponseInvoiceCreate createResponse = new Gson().fromJson(future.get().getBody().toString(), ResponseInvoiceCreate.class);
        if (createResponse.getStatus() == ResponseInvoiceCreate.Status.failure) {
            throw new IllegalStateException("unable to create invoice\n" + future.get().getBody());
        }

        String requestId = createResponse.getRequestId();
        String eventDate = createResponse.getEventDate();

        logger.info("*** requestId: " + requestId);

        //as the previous call was async, might need to try a few times until the invoice is created
        InvoiceChangeLog changeLog = getInvoiceChangeLog(requestId, eventDate);
        while (changeLog == null) {
            Thread.sleep(1_000L);
            changeLog = getInvoiceChangeLog(requestId, eventDate);

            //even if not null, might still be pending or failure
            if (changeLog != null) {
                switch (changeLog.getStatus())
                {
                    case failure:
                        throw new IllegalStateException(MessageFormat.format(
                                "unable to create invoice: {0}: {1}",
                                changeLog.getResults().get(0).getCode(), changeLog.getResults().get(0).getDescription()));
                    case success:
                        break;
                    case pending:
                        logger.info("invoice state pending. trying again...");
                        changeLog = null;
                        break;
                }

            } else {
                logger.info("no matching request found in logs. trying again...");
            }
        }

        logger.info("invoice successfully created");

        return null;
    }

    private InvoiceChangeLog getInvoiceChangeLog(String requestId, String eventDate)
    throws Exception
    {
        //due to propagation delay issues, recommended best practice is to shift everything back by 10 seconds and begin searching from then
        ZonedDateTime from = ZonedDateTime.parse(eventDate).minusSeconds(10L);
        String fromDateMinus10Seconds = Util.dateToIso8601(Date.from(from.toInstant()));

        String nowMinus10Seconds = Util.dateToIso8601(new Date(System.currentTimeMillis()-10_000L));

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return invoiceV3Api.getInvoiceChangeLog(fromDateMinus10Seconds, nowMinus10Seconds, null, null);
        }, invoiceV3Api, logger, "getInvoiceChangeLog", NetworkExecutor.HTTP_RESPONSE_200);

logger.info(future.get().getBody());

        //convert to java and look for the given requestId
        InvoiceChangeLog requestedResponseRecord = null;
        ResponseInvoiceChangeLog response = new Gson().fromJson(future.get().getBody().toString(), ResponseInvoiceChangeLog.class);

        //TODO: server bug causing the requestId's not to match up, so for now, take the first row and just use it, since
        // on stage, it's 99% likely it's the one we're looking for
        if (response.getLogs() != null && response.getLogs().size() > 0) {
            return response.getLogs().get(0);
        } else {
            return null;
        }

//TODO: put this back once the server bug gets fixed2
//        for (InvoiceChangeLog log : response.getLogs()) {
//            if (log.getRequestId().equals(requestId)) {
//                requestedResponseRecord = log;
//                break;
//            }
//        }
//        return requestedResponseRecord;
    }
}
