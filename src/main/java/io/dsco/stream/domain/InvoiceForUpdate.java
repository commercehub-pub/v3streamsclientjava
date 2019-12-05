package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvoiceForUpdate
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

    public InvoiceForUpdate() {}

    public InvoiceForUpdate(
            @NotNull String poNumber, @NotNull String invoiceId, float totalAmount,
            @NotNull List<InvoiceLineItemForUpdate> lineItems, InvoiceShipInfo shipInfo)
    {
        this.poNumber = poNumber;
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.lineItems = lineItems;
        this.ship = shipInfo;
    }

    public String getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public float getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getBuyerId()
    {
        return buyerId;
    }

    public void setBuyerId(String buyerId)
    {
        this.buyerId = buyerId;
    }

    public List<InvoiceCharge> getCharges()
    {
        return charges;
    }

    public void setCharges(List<InvoiceCharge> charges)
    {
        this.charges = charges;
    }

    public String getConsumerOrderNumber()
    {
        return consumerOrderNumber;
    }

    public void setConsumerOrderNumber(String consumerOrderNumber)
    {
        this.consumerOrderNumber = consumerOrderNumber;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getDscoOrderId()
    {
        return dscoOrderId;
    }

    public void setDscoOrderId(String dscoOrderId)
    {
        this.dscoOrderId = dscoOrderId;
    }

    public Float getExpectedOrderTotalAmount()
    {
        return expectedOrderTotalAmount;
    }

    public void setExpectedOrderTotalAmount(Float expectedOrderTotalAmount)
    {
        this.expectedOrderTotalAmount = expectedOrderTotalAmount;
    }

    public Float getExpectedOrderTotalDifference()
    {
        return expectedOrderTotalDifference;
    }

    public void setExpectedOrderTotalDifference(Float expectedOrderTotalDifference)
    {
        this.expectedOrderTotalDifference = expectedOrderTotalDifference;
    }

    public String getExternalBatchId()
    {
        return externalBatchId;
    }

    public void setExternalBatchId(String externalBatchId)
    {
        this.externalBatchId = externalBatchId;
    }

    public Float getFreightAmount()
    {
        return freightAmount;
    }

    public void setFreightAmount(Float freightAmount)
    {
        this.freightAmount = freightAmount;
    }

    public Float getHandlingAmount()
    {
        return handlingAmount;
    }

    public void setHandlingAmount(Float handlingAmount)
    {
        this.handlingAmount = handlingAmount;
    }

    public String getInvoiceDate()
    {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    public List<InvoiceLineItemForUpdate> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<InvoiceLineItemForUpdate> lineItems)
    {
        this.lineItems = lineItems;
    }

    public Float getLineItemsSubtotal()
    {
        return lineItemsSubtotal;
    }

    public void setLineItemsSubtotal(Float lineItemsSubtotal)
    {
        this.lineItemsSubtotal = lineItemsSubtotal;
    }

    public Integer getNumberOfLineItems()
    {
        return numberOfLineItems;
    }

    public void setNumberOfLineItems(Integer numberOfLineItems)
    {
        this.numberOfLineItems = numberOfLineItems;
    }

    public String getPoNumber()
    {
        return poNumber;
    }

    public void setPoNumber(String poNumber)
    {
        this.poNumber = poNumber;
    }

    public Float getSalesTaxAmount()
    {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(Float salesTaxAmount)
    {
        this.salesTaxAmount = salesTaxAmount;
    }

    public String getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(String sellerId)
    {
        this.sellerId = sellerId;
    }

    public String getSellerInvoiceNumber()
    {
        return sellerInvoiceNumber;
    }

    public void setSellerInvoiceNumber(String sellerInvoiceNumber)
    {
        this.sellerInvoiceNumber = sellerInvoiceNumber;
    }

    public InvoiceShipInfo getShip()
    {
        return ship;
    }

    public void setShip(InvoiceShipInfo ship)
    {
        this.ship = ship;
    }

    public InvoiceShipFromTo getShipFrom()
    {
        return shipFrom;
    }

    public void setShipFrom(InvoiceShipFromTo shipFrom)
    {
        this.shipFrom = shipFrom;
    }

    public InvoiceShipFromTo getShipTo()
    {
        return shipTo;
    }

    public void setShipTo(InvoiceShipFromTo shipTo)
    {
        this.shipTo = shipTo;
    }

    public Float getSubtotalExcludingLineItems()
    {
        return subtotalExcludingLineItems;
    }

    public void setSubtotalExcludingLineItems(Float subtotalExcludingLineItems)
    {
        this.subtotalExcludingLineItems = subtotalExcludingLineItems;
    }

    public String getSupplierOrderNumber()
    {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber)
    {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public InvoiceTerms getTerms()
    {
        return terms;
    }

    public void setTerms(InvoiceTerms terms)
    {
        this.terms = terms;
    }
}
