package io.dsco.demo.scenario;

import io.dsco.demo.Util;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.command.supplier.CreateInvoiceSmallBatch;
import io.dsco.stream.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

public class OrderInvoice
{
    private static final Logger logger = LogManager.getLogger(OrderInvoice.class);
    private static final String SCENARIO_NAME = "Order Invoice";

    private final CreateInvoiceSmallBatch createInvoiceSmallBatchCmd;

    public OrderInvoice(InvoiceV3Api invoiceV3ApiSupplier)
    {
        createInvoiceSmallBatchCmd = new CreateInvoiceSmallBatch(invoiceV3ApiSupplier);
    }

    public InvoiceForUpdate begin(Order order)
    throws Exception
    {
        long b = System.currentTimeMillis();
        logger.info(MessageFormat.format("***** running scenario: {0} *****", SCENARIO_NAME));

        //create an invoice for the order (supplier)
        InvoiceForUpdate invoice = createInvoiceObject(order);
        createInvoiceSmallBatchCmd.execute(Collections.singletonList(invoice));

        long e = System.currentTimeMillis();
        logger.info(MessageFormat.format("total time (ms): {0}", (e-b)));
        logger.info(MessageFormat.format("***** {0} scenario complete *****", SCENARIO_NAME));

        return invoice;
    }

    private InvoiceForUpdate createInvoiceObject(Order order)
    {
        String invoiceId = order.getPoNumber(); //match the po# unless there's a specific reason not to
        float totalAmount = 0.0F;

        List<InvoiceLineItemForUpdate> lineItems = new ArrayList<>(order.getLineItems().size());
        for (OrderLineItem lineItem : order.getLineItems()) {

            float retailerPrice;
            if (lineItem.getRetailerPrice() == null) {
                retailerPrice = 1.0F;
            } else {
                retailerPrice = lineItem.getRetailerPrice();
            }
            totalAmount += retailerPrice;

            lineItems.add(new InvoiceLineItemForUpdate(
                    lineItem.getQuantity(), lineItem.getDscoItemId(), lineItem.getEan(), lineItem.getPartnerSku(), lineItem.getSku(), lineItem.getUpc(), retailerPrice)
            );
        }

        InvoiceShipInfo invoiceShipInfo = new InvoiceShipInfo(Util.dateToIso8601(new Date()), "1234567891234", order.getShippingServiceLevelCode());

        return new InvoiceForUpdate(order.getPoNumber(), invoiceId, totalAmount, lineItems, invoiceShipInfo);
    }
}
