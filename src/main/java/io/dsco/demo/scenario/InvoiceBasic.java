package io.dsco.demo.scenario;

import io.dsco.demo.scenario.base.BasicStreamProcessor;
import io.dsco.demo.scenario.base.CommonStreamMethods;
import io.dsco.demo.scenario.base.InvoiceMethods;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class InvoiceBasic
implements CommonStreamMethods
{
    private static final Logger logger = LogManager.getLogger(InvoiceBasic.class);
    private static final String SCENARIO_NAME = "Basic Invoice Stream Processing";

    private final StreamV3Api streamV3ApiRetailer;
    private final String streamId;
    private final InvoiceV3Api invoiceV3ApiSupplier;

    private final BasicStreamProcessor<StreamItemInvoice> basicStreamProcessor;
    private final InvoiceMethods invoiceMethods;

    public InvoiceBasic(StreamV3Api streamV3ApiRetailer, String streamId, InvoiceV3Api invoiceV3ApiSupplier)
    {
        this.streamV3ApiRetailer = streamV3ApiRetailer;
        this.streamId = streamId;
        this.invoiceV3ApiSupplier = invoiceV3ApiSupplier;

        basicStreamProcessor = new BasicStreamProcessor<>(logger, streamV3ApiRetailer, streamId);
        invoiceMethods = new InvoiceMethods(streamV3ApiRetailer, streamId, logger);
    }

    public void begin(String invoiceId)
    {
        try {
            long b = System.currentTimeMillis();
            logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

            //step 1: create an invoice
            InvoiceForUpdate invoice = createInvoice(invoiceId);

            //step 2: add the invoice to the system
            ResponseInvoiceCreate response = invoiceMethods.createInvoiceSmallBatch(invoiceV3ApiSupplier, Collections.singletonList(invoice));
            //in a non-demo you'd want to check for errors
            String requestId = response.getRequestId();
            String eventDate = response.getEventDate();

            //step 3: wait until the invoice has been processed
            waitUntilInvoiceHasBeenProcessed(requestId, eventDate);

            //step 4: check the stream for the invoice
            String streamPosition = getStreamPosition(streamV3ApiRetailer, streamId, logger);
            logger.info("initial stream position: " + streamPosition);

            basicStreamProcessor.processAllItemsInStream(streamPosition, invoiceMethods::getInvoiceEventsFromPosition);

            long e = System.currentTimeMillis();
            logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
            logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        } catch (Exception e) {
            logger.error("unhandled exception", e);
        }
    }

    private InvoiceForUpdate createInvoice(String invoiceId)
    {
        List<InvoiceCharge> charges = Collections.singletonList(
                new InvoiceCharge(1.0F, "something line item")
        );

        List<InvoiceLineItemForUpdate> lineItems = Collections.singletonList(
                new InvoiceLineItemForUpdate(1, null, null, null, UUID.randomUUID().toString(), null)
        );

        return new InvoiceForUpdate(invoiceId, 1.0F, charges, lineItems);
    }

    private void waitUntilInvoiceHasBeenProcessed(String requestId, String eventDate)
    throws ExecutionException, InterruptedException
    {
        InvoiceChangeLog log = invoiceMethods.getInvoiceChangeLog(invoiceV3ApiSupplier, requestId, eventDate);
        if (log == null) {
            throw new IllegalStateException("invoice response record not found");
        }

        switch (log.getStatus())
        {
            case failure:
                throw new IllegalStateException(MessageFormat.format("unable to add invoice:\n{0}", log.getResultsAsJson()));

            case pending:
                if (logger.isDebugEnabled()) {
                    logger.debug("invoice is still pending. waiting a bit and trying again...");
                }
                Thread.sleep(500);
                waitUntilInvoiceHasBeenProcessed(requestId, eventDate);
                break;

            case success:
                if (logger.isDebugEnabled()) {
                    logger.debug("invoice has been processed");
                }
                break;
        }
    }

}
