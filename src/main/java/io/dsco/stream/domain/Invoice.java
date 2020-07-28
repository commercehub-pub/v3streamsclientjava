package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class Invoice {
	//MEMBERS
	private String invoiceId;
	/* this or poNumber or supplierOrderNumber is required to identify the order this invoice is associated with */
	private String dscoOrderId;
	/* this or dscoOrderId or supplierOrderNumber is required to identify the order this invoice is associated with */
	private String poNumber;
	/* this or dscoOrderId or poNumber is required to identify the order this invoice is associated with */
	private String supplierOrderNumber;
	private Iso8601DateTime invoiceDate;
	private String currencyCode;
	private float totalAmount;
	private Float lineItemsSubtotal;
	private Float subtotalExcludingLineItems;
	private Integer numberOfLineItems;
	private Float handlingAmount;
	private Float freightAmount;
	private Float salesTaxAmount;
	private String buyerId;
	private String sellerId;
	private String sellerInvoiceNumber;
	private String consumerOrderNumber;
	private String externalBatchId;
	private Float expectedOrderTotalAmount;
	private Float expectedOrderTotalDifference;
	private List<InvoiceCharge> charges;
	/* The InvoiceTerms JSON object defines the terms of an invoice in the Dsco system.

At least one attribute must be provided for this to be a valid object. */
	private InvoiceTerms terms;
	/* The InvoiceShipFromTo JSON object defines the ship from/to of an invoice in the Dsco system.

At least one attribute must be provided for this to be a valid object. */
	private InvoiceShipFromTo shipFrom;
	/* The InvoiceShipFromTo JSON object defines the ship from/to of an invoice in the Dsco system.

At least one attribute must be provided for this to be a valid object. */
	private InvoiceShipFromTo shipTo;
	/* The InvoiceShipInfo JSON object defines the ship info of an invoice in the Dsco system.

At least one attribute must be provided for this to be a valid object. */
	private InvoiceShipInfo ship;
	private List<InvoiceLineItem> lineItems;
	private Float dscoExpectedOrderTotalAmount;
	private Float dscoExpectedOrderTotalDifference;
	private String dscoSupplierId;
	private String dscoRetailerId;
	private String dscoTradingPartnerId;
	private String dscoTradingPartnerName;
	private String requestedWarehouseCode;
	private String requestedWarehouseRetailerCode;
	private String requestedWarehouseDscoId;
	private Iso8601DateTime exportDate;
	private Iso8601DateTime createDate;
	private Iso8601DateTime lastUpdate;

	//CONSTRUCTORS
	public Invoice() {}

	//ACCESSORS / MUTATORS
	public String getInvoiceId() { return invoiceId; }
	public void setInvoiceId(String val) { invoiceId = val; }
	public String getDscoOrderId() { return dscoOrderId; }
	public void setDscoOrderId(String val) { dscoOrderId = val; }
	public String getPoNumber() { return poNumber; }
	public void setPoNumber(String val) { poNumber = val; }
	public String getSupplierOrderNumber() { return supplierOrderNumber; }
	public void setSupplierOrderNumber(String val) { supplierOrderNumber = val; }
	public Iso8601DateTime getInvoiceDate() { return invoiceDate; }
	public void setInvoiceDate(Iso8601DateTime val) { invoiceDate = val; }
	public String getCurrencyCode() { return currencyCode; }
	public void setCurrencyCode(String val) { currencyCode = val; }
	public float getTotalAmount() { return totalAmount; }
	public void setTotalAmount(float val) { totalAmount = val; }
	public Float getLineItemsSubtotal() { return lineItemsSubtotal; }
	public void setLineItemsSubtotal(Float val) { lineItemsSubtotal = val; }
	public Float getSubtotalExcludingLineItems() { return subtotalExcludingLineItems; }
	public void setSubtotalExcludingLineItems(Float val) { subtotalExcludingLineItems = val; }
	public Integer getNumberOfLineItems() { return numberOfLineItems; }
	public void setNumberOfLineItems(Integer val) { numberOfLineItems = val; }
	public Float getHandlingAmount() { return handlingAmount; }
	public void setHandlingAmount(Float val) { handlingAmount = val; }
	public Float getFreightAmount() { return freightAmount; }
	public void setFreightAmount(Float val) { freightAmount = val; }
	public Float getSalesTaxAmount() { return salesTaxAmount; }
	public void setSalesTaxAmount(Float val) { salesTaxAmount = val; }
	public String getBuyerId() { return buyerId; }
	public void setBuyerId(String val) { buyerId = val; }
	public String getSellerId() { return sellerId; }
	public void setSellerId(String val) { sellerId = val; }
	public String getSellerInvoiceNumber() { return sellerInvoiceNumber; }
	public void setSellerInvoiceNumber(String val) { sellerInvoiceNumber = val; }
	public String getConsumerOrderNumber() { return consumerOrderNumber; }
	public void setConsumerOrderNumber(String val) { consumerOrderNumber = val; }
	public String getExternalBatchId() { return externalBatchId; }
	public void setExternalBatchId(String val) { externalBatchId = val; }
	public Float getExpectedOrderTotalAmount() { return expectedOrderTotalAmount; }
	public void setExpectedOrderTotalAmount(Float val) { expectedOrderTotalAmount = val; }
	public Float getExpectedOrderTotalDifference() { return expectedOrderTotalDifference; }
	public void setExpectedOrderTotalDifference(Float val) { expectedOrderTotalDifference = val; }
	public List<InvoiceCharge> getCharges() { return charges; }
	public void setCharges(List<InvoiceCharge> val) { charges = val; }
	public InvoiceTerms getTerms() { return terms; }
	public void setTerms(InvoiceTerms val) { terms = val; }
	public InvoiceShipFromTo getShipFrom() { return shipFrom; }
	public void setShipFrom(InvoiceShipFromTo val) { shipFrom = val; }
	public InvoiceShipFromTo getShipTo() { return shipTo; }
	public void setShipTo(InvoiceShipFromTo val) { shipTo = val; }
	public InvoiceShipInfo getShip() { return ship; }
	public void setShip(InvoiceShipInfo val) { ship = val; }
	public List<InvoiceLineItem> getLineItems() { return lineItems; }
	public void setLineItems(List<InvoiceLineItem> val) { lineItems = val; }
	public Float getDscoExpectedOrderTotalAmount() { return dscoExpectedOrderTotalAmount; }
	public void setDscoExpectedOrderTotalAmount(Float val) { dscoExpectedOrderTotalAmount = val; }
	public Float getDscoExpectedOrderTotalDifference() { return dscoExpectedOrderTotalDifference; }
	public void setDscoExpectedOrderTotalDifference(Float val) { dscoExpectedOrderTotalDifference = val; }
	public String getDscoSupplierId() { return dscoSupplierId; }
	public void setDscoSupplierId(String val) { dscoSupplierId = val; }
	public String getDscoRetailerId() { return dscoRetailerId; }
	public void setDscoRetailerId(String val) { dscoRetailerId = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
	public String getDscoTradingPartnerName() { return dscoTradingPartnerName; }
	public void setDscoTradingPartnerName(String val) { dscoTradingPartnerName = val; }
	public String getRequestedWarehouseCode() { return requestedWarehouseCode; }
	public void setRequestedWarehouseCode(String val) { requestedWarehouseCode = val; }
	public String getRequestedWarehouseRetailerCode() { return requestedWarehouseRetailerCode; }
	public void setRequestedWarehouseRetailerCode(String val) { requestedWarehouseRetailerCode = val; }
	public String getRequestedWarehouseDscoId() { return requestedWarehouseDscoId; }
	public void setRequestedWarehouseDscoId(String val) { requestedWarehouseDscoId = val; }
	public Iso8601DateTime getExportDate() { return exportDate; }
	public void setExportDate(Iso8601DateTime val) { exportDate = val; }
	public Iso8601DateTime getCreateDate() { return createDate; }
	public void setCreateDate(Iso8601DateTime val) { createDate = val; }
	public Iso8601DateTime getLastUpdate() { return lastUpdate; }
	public void setLastUpdate(Iso8601DateTime val) { lastUpdate = val; }
}