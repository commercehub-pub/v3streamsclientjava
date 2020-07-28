package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Order {
	//ENUMS
	public enum DSCO_STATUS {
		@SerializedName("created") CREATED,
		@SerializedName("shipment_pending") SHIPMENT_PENDING,
		@SerializedName("shipped") SHIPPED,
		@SerializedName("cancelled") CANCELLED
	}
	public enum DSCO_LIFECYCLE {
		@SerializedName("received") RECEIVED,
		@SerializedName("created") CREATED,
		@SerializedName("acknowledged") ACKNOWLEDGED,
		@SerializedName("completed") COMPLETED
	}

	//MEMBERS
	private String poNumber;
	private List<OrderLineItem> lineItems;
	/* When the retailer created the order */
	private Iso8601DateTime retailerCreateDate;
	private String channel;
	/* The OrderShipping object defines whom and where an order will be shipped to 
as data within an Order in the DSCO system. */
	private OrderShipping shipping;
	private Boolean signatureRequiredFlag;
	private String shipInstructions;
	private Boolean giftWrapFlag;
	private String giftWrapMessage;
	private String supplierOrderNumber;
	/* Dsco's order ID */
	private String dscoOrderId;
	/* this attribute is deprecated, please use dscoLifecycle */
	private DSCO_STATUS dscoStatus;
	/* Use this in favor of dscoStatus which is deprecated. */
	private DSCO_LIFECYCLE dscoLifecycle;
	/* Dsco's ID for the retailer that owns this order */
	private String dscoRetailerId;
	/* Dsco's ID for the supplier that will fulfill this order */
	private String dscoSupplierId;
	/* Dsco's name for the supplier that will fulfill this order */
	private String dscoSupplierName;
	private String dscoTradingPartnerId;
	private String dscoTradingPartnerName;
	/* the date Dsco created this order in its system */
	private Iso8601DateTime dscoCreateDate;
	/* the date Dsco last updated this order in its system */
	private Iso8601DateTime dscoLastUpdate;
	/* if true, this is a test order in the Dsco system */
	private Boolean testFlag;
	private String currencyCode;
	private Iso8601DateTime cancelAfterDate;
	private String consumerOrderNumber;
	private Iso8601DateTime shipByDate;
	private Iso8601DateTime acknowledgeByDate;
	private Iso8601DateTime invoiceByDate;
	private Iso8601DateTime dscoShipLateDate;
	private Iso8601DateTime dscoAcknowledgeLateDate;
	private Iso8601DateTime dscoCancelLateDate;
	private Iso8601DateTime dscoInvoiceLateDate;
	private Iso8601DateTime expectedDeliveryDate;
	private Iso8601DateTime requiredDeliveryDate;
	private String marketingMessage;
	private String message;
	private String packingInstructions;
	private String shipToStoreNumber;
	private Integer numberOfLineItems;
	private Boolean giftFlag;
	private String giftMessage;
	private String giftToName;
	private String giftFromName;
	private String receiptId;
	private String giftReceiptId;
	private Float shippingSurcharge;
	private List<OrderTax> taxes;
	private List<OrderCoupon> coupons;
	private List<OrderPayment> payments;
	/* The OrderBillTo object defines the bill to of an Order in the DSCO system. */
	private OrderBillTo billTo;
	/* Either shippingServiceLevelCode must be present or shipCarrier and shipMethod must be present. */
	private String shipCarrier;
	/* Either shippingServiceLevelCode must be present or shipCarrier and shipMethod must be present. */
	private String shipMethod;
	/* Either shippingServiceLevelCode must be present or shipCarrier and shipMethod must be present. */
	private String shippingServiceLevelCode;
	private String requestedShipCarrier;
	private String requestedShipMethod;
	private String requestedShippingServiceLevelCode;
	private String retailerShipCarrier;
	private String retailerShipMethod;
	private String retailerShippingServiceLevelCode;
	private String dscoShipCarrier;
	private String dscoShipMethod;
	private String dscoShippingServiceLevelCode;
	private List<Package> packages;
	private String requestedWarehouseCode;
	private String requestedWarehouseRetailerCode;
	private String requestedWarehouseDscoId;
	private String dscoWarehouseCode;
	private String dscoWarehouseRetailerCode;
	private String dscoWarehouseDscoId;
	private String shipWarehouseCode;
	private String shipWarehouseRetailerCode;
	private String shipWarehouseDscoId;
	private String packingSlipMessage;

	//CONSTRUCTORS
	public Order() {}

	//ACCESSORS / MUTATORS
	public String getPoNumber() { return poNumber; }
	public void setPoNumber(String val) { poNumber = val; }
	public List<OrderLineItem> getLineItems() { return lineItems; }
	public void setLineItems(List<OrderLineItem> val) { lineItems = val; }
	public Iso8601DateTime getRetailerCreateDate() { return retailerCreateDate; }
	public void setRetailerCreateDate(Iso8601DateTime val) { retailerCreateDate = val; }
	public String getChannel() { return channel; }
	public void setChannel(String val) { channel = val; }
	public OrderShipping getShipping() { return shipping; }
	public void setShipping(OrderShipping val) { shipping = val; }
	public Boolean getSignatureRequiredFlag() { return signatureRequiredFlag; }
	public void setSignatureRequiredFlag(Boolean val) { signatureRequiredFlag = val; }
	public String getShipInstructions() { return shipInstructions; }
	public void setShipInstructions(String val) { shipInstructions = val; }
	public Boolean getGiftWrapFlag() { return giftWrapFlag; }
	public void setGiftWrapFlag(Boolean val) { giftWrapFlag = val; }
	public String getGiftWrapMessage() { return giftWrapMessage; }
	public void setGiftWrapMessage(String val) { giftWrapMessage = val; }
	public String getSupplierOrderNumber() { return supplierOrderNumber; }
	public void setSupplierOrderNumber(String val) { supplierOrderNumber = val; }
	public String getDscoOrderId() { return dscoOrderId; }
	public void setDscoOrderId(String val) { dscoOrderId = val; }
	public DSCO_STATUS getDscoStatus() { return dscoStatus; }
	public void setDscoStatus(DSCO_STATUS val) { dscoStatus = val; }
	public DSCO_LIFECYCLE getDscoLifecycle() { return dscoLifecycle; }
	public void setDscoLifecycle(DSCO_LIFECYCLE val) { dscoLifecycle = val; }
	public String getDscoRetailerId() { return dscoRetailerId; }
	public void setDscoRetailerId(String val) { dscoRetailerId = val; }
	public String getDscoSupplierId() { return dscoSupplierId; }
	public void setDscoSupplierId(String val) { dscoSupplierId = val; }
	public String getDscoSupplierName() { return dscoSupplierName; }
	public void setDscoSupplierName(String val) { dscoSupplierName = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
	public String getDscoTradingPartnerName() { return dscoTradingPartnerName; }
	public void setDscoTradingPartnerName(String val) { dscoTradingPartnerName = val; }
	public Iso8601DateTime getDscoCreateDate() { return dscoCreateDate; }
	public void setDscoCreateDate(Iso8601DateTime val) { dscoCreateDate = val; }
	public Iso8601DateTime getDscoLastUpdate() { return dscoLastUpdate; }
	public void setDscoLastUpdate(Iso8601DateTime val) { dscoLastUpdate = val; }
	public Boolean getTestFlag() { return testFlag; }
	public void setTestFlag(Boolean val) { testFlag = val; }
	public String getCurrencyCode() { return currencyCode; }
	public void setCurrencyCode(String val) { currencyCode = val; }
	public Iso8601DateTime getCancelAfterDate() { return cancelAfterDate; }
	public void setCancelAfterDate(Iso8601DateTime val) { cancelAfterDate = val; }
	public String getConsumerOrderNumber() { return consumerOrderNumber; }
	public void setConsumerOrderNumber(String val) { consumerOrderNumber = val; }
	public Iso8601DateTime getShipByDate() { return shipByDate; }
	public void setShipByDate(Iso8601DateTime val) { shipByDate = val; }
	public Iso8601DateTime getAcknowledgeByDate() { return acknowledgeByDate; }
	public void setAcknowledgeByDate(Iso8601DateTime val) { acknowledgeByDate = val; }
	public Iso8601DateTime getInvoiceByDate() { return invoiceByDate; }
	public void setInvoiceByDate(Iso8601DateTime val) { invoiceByDate = val; }
	public Iso8601DateTime getDscoShipLateDate() { return dscoShipLateDate; }
	public void setDscoShipLateDate(Iso8601DateTime val) { dscoShipLateDate = val; }
	public Iso8601DateTime getDscoAcknowledgeLateDate() { return dscoAcknowledgeLateDate; }
	public void setDscoAcknowledgeLateDate(Iso8601DateTime val) { dscoAcknowledgeLateDate = val; }
	public Iso8601DateTime getDscoCancelLateDate() { return dscoCancelLateDate; }
	public void setDscoCancelLateDate(Iso8601DateTime val) { dscoCancelLateDate = val; }
	public Iso8601DateTime getDscoInvoiceLateDate() { return dscoInvoiceLateDate; }
	public void setDscoInvoiceLateDate(Iso8601DateTime val) { dscoInvoiceLateDate = val; }
	public Iso8601DateTime getExpectedDeliveryDate() { return expectedDeliveryDate; }
	public void setExpectedDeliveryDate(Iso8601DateTime val) { expectedDeliveryDate = val; }
	public Iso8601DateTime getRequiredDeliveryDate() { return requiredDeliveryDate; }
	public void setRequiredDeliveryDate(Iso8601DateTime val) { requiredDeliveryDate = val; }
	public String getMarketingMessage() { return marketingMessage; }
	public void setMarketingMessage(String val) { marketingMessage = val; }
	public String getMessage() { return message; }
	public void setMessage(String val) { message = val; }
	public String getPackingInstructions() { return packingInstructions; }
	public void setPackingInstructions(String val) { packingInstructions = val; }
	public String getShipToStoreNumber() { return shipToStoreNumber; }
	public void setShipToStoreNumber(String val) { shipToStoreNumber = val; }
	public Integer getNumberOfLineItems() { return numberOfLineItems; }
	public void setNumberOfLineItems(Integer val) { numberOfLineItems = val; }
	public Boolean getGiftFlag() { return giftFlag; }
	public void setGiftFlag(Boolean val) { giftFlag = val; }
	public String getGiftMessage() { return giftMessage; }
	public void setGiftMessage(String val) { giftMessage = val; }
	public String getGiftToName() { return giftToName; }
	public void setGiftToName(String val) { giftToName = val; }
	public String getGiftFromName() { return giftFromName; }
	public void setGiftFromName(String val) { giftFromName = val; }
	public String getReceiptId() { return receiptId; }
	public void setReceiptId(String val) { receiptId = val; }
	public String getGiftReceiptId() { return giftReceiptId; }
	public void setGiftReceiptId(String val) { giftReceiptId = val; }
	public Float getShippingSurcharge() { return shippingSurcharge; }
	public void setShippingSurcharge(Float val) { shippingSurcharge = val; }
	public List<OrderTax> getTaxes() { return taxes; }
	public void setTaxes(List<OrderTax> val) { taxes = val; }
	public List<OrderCoupon> getCoupons() { return coupons; }
	public void setCoupons(List<OrderCoupon> val) { coupons = val; }
	public List<OrderPayment> getPayments() { return payments; }
	public void setPayments(List<OrderPayment> val) { payments = val; }
	public OrderBillTo getBillTo() { return billTo; }
	public void setBillTo(OrderBillTo val) { billTo = val; }
	public String getShipCarrier() { return shipCarrier; }
	public void setShipCarrier(String val) { shipCarrier = val; }
	public String getShipMethod() { return shipMethod; }
	public void setShipMethod(String val) { shipMethod = val; }
	public String getShippingServiceLevelCode() { return shippingServiceLevelCode; }
	public void setShippingServiceLevelCode(String val) { shippingServiceLevelCode = val; }
	public String getRequestedShipCarrier() { return requestedShipCarrier; }
	public void setRequestedShipCarrier(String val) { requestedShipCarrier = val; }
	public String getRequestedShipMethod() { return requestedShipMethod; }
	public void setRequestedShipMethod(String val) { requestedShipMethod = val; }
	public String getRequestedShippingServiceLevelCode() { return requestedShippingServiceLevelCode; }
	public void setRequestedShippingServiceLevelCode(String val) { requestedShippingServiceLevelCode = val; }
	public String getRetailerShipCarrier() { return retailerShipCarrier; }
	public void setRetailerShipCarrier(String val) { retailerShipCarrier = val; }
	public String getRetailerShipMethod() { return retailerShipMethod; }
	public void setRetailerShipMethod(String val) { retailerShipMethod = val; }
	public String getRetailerShippingServiceLevelCode() { return retailerShippingServiceLevelCode; }
	public void setRetailerShippingServiceLevelCode(String val) { retailerShippingServiceLevelCode = val; }
	public String getDscoShipCarrier() { return dscoShipCarrier; }
	public void setDscoShipCarrier(String val) { dscoShipCarrier = val; }
	public String getDscoShipMethod() { return dscoShipMethod; }
	public void setDscoShipMethod(String val) { dscoShipMethod = val; }
	public String getDscoShippingServiceLevelCode() { return dscoShippingServiceLevelCode; }
	public void setDscoShippingServiceLevelCode(String val) { dscoShippingServiceLevelCode = val; }
	public List<Package> getPackages() { return packages; }
	public void setPackages(List<Package> val) { packages = val; }
	public String getRequestedWarehouseCode() { return requestedWarehouseCode; }
	public void setRequestedWarehouseCode(String val) { requestedWarehouseCode = val; }
	public String getRequestedWarehouseRetailerCode() { return requestedWarehouseRetailerCode; }
	public void setRequestedWarehouseRetailerCode(String val) { requestedWarehouseRetailerCode = val; }
	public String getRequestedWarehouseDscoId() { return requestedWarehouseDscoId; }
	public void setRequestedWarehouseDscoId(String val) { requestedWarehouseDscoId = val; }
	public String getDscoWarehouseCode() { return dscoWarehouseCode; }
	public void setDscoWarehouseCode(String val) { dscoWarehouseCode = val; }
	public String getDscoWarehouseRetailerCode() { return dscoWarehouseRetailerCode; }
	public void setDscoWarehouseRetailerCode(String val) { dscoWarehouseRetailerCode = val; }
	public String getDscoWarehouseDscoId() { return dscoWarehouseDscoId; }
	public void setDscoWarehouseDscoId(String val) { dscoWarehouseDscoId = val; }
	public String getShipWarehouseCode() { return shipWarehouseCode; }
	public void setShipWarehouseCode(String val) { shipWarehouseCode = val; }
	public String getShipWarehouseRetailerCode() { return shipWarehouseRetailerCode; }
	public void setShipWarehouseRetailerCode(String val) { shipWarehouseRetailerCode = val; }
	public String getShipWarehouseDscoId() { return shipWarehouseDscoId; }
	public void setShipWarehouseDscoId(String val) { shipWarehouseDscoId = val; }
	public String getPackingSlipMessage() { return packingSlipMessage; }
	public void setPackingSlipMessage(String val) { packingSlipMessage = val; }
}