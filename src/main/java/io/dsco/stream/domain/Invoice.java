package io.dsco.stream.domain;

import java.util.List;

public class Invoice
{
    private String invoiceId;
    private float totalAmount;
    private String buyerId;
    private List<InvoiceCharge> charges;
    private String consumerOrderNumber;
    private String currencyCode;
    private String dscoOrderId;
    private Float expectedOrderTotalAmount;
    private Float expectedOrderTotalDifference;
    private String externalBatchId;
    private Float freightAmount;
    private Float handlingAmount;
    private String invoiceDate; //iso8601
    private List<InvoiceLineItemForUpdate> lineItems;
    private Float lineItemsSubtotal;
    private Integer numberOfLineItems;
    private String poNumber;
    private Float salesTaxAmount;
    private String sellerId;
    private String sellerInvoiceNumber;
    private InvoiceShipInfo ship;
    private InvoiceShipFromTo shipFrom;
    private InvoiceShipFromTo shipTo;
    private Float subtotalExcludingLineItems;
    private String supplierOrderNumber;
    private InvoiceTerms terms;
}
