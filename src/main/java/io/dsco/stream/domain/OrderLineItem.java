package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderLineItem {
	//ENUMS
	public enum STATUS {
		@SerializedName("accepted") ACCEPTED,
		@SerializedName("rejected") REJECTED,
		@SerializedName("cancelled") CANCELLED
	}

	//MEMBERS
	/* The number of this item on the order */
	private int quantity;
	/* The supplier of this item, Dsco's ID for it - one of the ID's must be present */
	private String dscoSupplierId;
	/* The supplier of this item, The trading partner's ID for it  - one of the ID's must be present */
	private String dscoTradingPartnerId;
	/* Dsco's unique ID for this item - one of the ID's must be present */
	private String dscoItemId;
	/* A unique ID for this item - one of the ID's must be present */
	private String sku;
	/* A unique ID for this item - one of the ID's must be present */
	private String upc;
	/* A unique ID for this item - one of the ID's must be present */
	private String ean;
	/* Optional unique ID for this item */
	private String mpn;
	/* Optional unique ID for this item */
	private String isbn;
	/* Optional unique ID for this item */
	private String gtin;
	/* When the shipment is expected to arrive. Can be set by a supplier when updating an order. */
	private Iso8601DateTime estimatedShipDate;
	/* A reason why estimatedShipDate was set. Can be set by a supplier when updating an order. */
	private String estimatedShipReason;
	/* If set, the value will be passed along to the supplier. It is a returns message to be included with the line item. */
	private String returnsMessage;
	/* A unique ID for this item - one of the ID's must be present */
	private String partnerSku;
	private String title;
	private Float expectedCost;
	private Float consumerPrice;
	private String personalization;
	/* The supplier's ID for the warehouse to use */
	private String warehouseCode;
	/* The retailer's ID for the warehouse to use */
	private String warehouseRetailerCode;
	/* Dsco's ID for the warehouse to use */
	private String warehouseDscoId;
	/* If some of the quantity of the order but not all has been cancelled or rejected
then the order may continue to be shipped; however, if all of the quantity of the 
order has been cancelled or rejected then the status will be cancelled; if this 
status has a value but cancelledQuantity, acceptedQuantity and rejectedQuantity 
are not set then this status applies to the entire quantity of the line item or 
to the portion of the quantity not expressly cancelled, rejected or accepted via 
cancelledQuantity, rejectedQuantity and acceptedQuantity respectively compared to 
overall quantity to determine line item status */
	private STATUS status;
	private String statusReason;
	/* If present and non-zero then
this is the quantity of the item that has
been cancelled by the supplier; if this 
quantity is less than the total quantity of
the item then look to acceptedQuantity and
rejectedQuantity to know the status of the
remainder of the quantity */
	private Integer cancelledQuantity;
	private String cancelledReason;
	private String cancelCode;
	/* If present and non-zero then
this is the quantity of the item that has 
been accepted; if this quantity is less than
the total quantity of the item then look to
cancelledQuantity and rejectedQuantity to 
know the status of the remainder of the 
quantity */
	private Integer acceptedQuantity;
	private String acceptedReason;
	/* If present and non-zero then
this is the quantity of the item that has
been rejected by the retailer; if this 
quantity is less than the total quantity of 
the item then look to cancelledQuantity and 
rejectedQuantity to know the status of the 
remainder of the quantity */
	private Integer rejectedQuantity;
	private String rejectedReason;
	/* Uniquely identifies a line item for the order */
	private Integer lineNumber;
	private String message;
	private String packingInstructions;
	private String shipInstructions;
	private String receiptId;
	private Boolean giftFlag;
	private String giftReceiptId;
	private String giftToName;
	private String giftFromName;
	private String giftMessage;
	private String color;
	private String size;
	private Float retailPrice;
	private Float shippingSurcharge;
	private List<OrderTax> taxes;
	private List<OrderLineItemActivity> activity;
	private List<String> retailerItemIds;
	private String departmentId;
	private String departmentName;
	private String merchandisingAccountId;
	private String merchandisingAccountName;
	private Boolean bogoFlag;
	private String bogoInstructions;
	/* The date of the change; this attribute is deprecated, please use activityDate */
	private Iso8601DateTime updateDate;

	//CONSTRUCTORS
	public OrderLineItem() {}

	//ACCESSORS / MUTATORS
	public int getQuantity() { return quantity; }
	public void setQuantity(int val) { quantity = val; }
	public String getDscoSupplierId() { return dscoSupplierId; }
	public void setDscoSupplierId(String val) { dscoSupplierId = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
	public String getDscoItemId() { return dscoItemId; }
	public void setDscoItemId(String val) { dscoItemId = val; }
	public String getSku() { return sku; }
	public void setSku(String val) { sku = val; }
	public String getUpc() { return upc; }
	public void setUpc(String val) { upc = val; }
	public String getEan() { return ean; }
	public void setEan(String val) { ean = val; }
	public String getMpn() { return mpn; }
	public void setMpn(String val) { mpn = val; }
	public String getIsbn() { return isbn; }
	public void setIsbn(String val) { isbn = val; }
	public String getGtin() { return gtin; }
	public void setGtin(String val) { gtin = val; }
	public Iso8601DateTime getEstimatedShipDate() { return estimatedShipDate; }
	public void setEstimatedShipDate(Iso8601DateTime val) { estimatedShipDate = val; }
	public String getEstimatedShipReason() { return estimatedShipReason; }
	public void setEstimatedShipReason(String val) { estimatedShipReason = val; }
	public String getReturnsMessage() { return returnsMessage; }
	public void setReturnsMessage(String val) { returnsMessage = val; }
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public String getTitle() { return title; }
	public void setTitle(String val) { title = val; }
	public Float getExpectedCost() { return expectedCost; }
	public void setExpectedCost(Float val) { expectedCost = val; }
	public Float getConsumerPrice() { return consumerPrice; }
	public void setConsumerPrice(Float val) { consumerPrice = val; }
	public String getPersonalization() { return personalization; }
	public void setPersonalization(String val) { personalization = val; }
	public String getWarehouseCode() { return warehouseCode; }
	public void setWarehouseCode(String val) { warehouseCode = val; }
	public String getWarehouseRetailerCode() { return warehouseRetailerCode; }
	public void setWarehouseRetailerCode(String val) { warehouseRetailerCode = val; }
	public String getWarehouseDscoId() { return warehouseDscoId; }
	public void setWarehouseDscoId(String val) { warehouseDscoId = val; }
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public String getStatusReason() { return statusReason; }
	public void setStatusReason(String val) { statusReason = val; }
	public Integer getCancelledQuantity() { return cancelledQuantity; }
	public void setCancelledQuantity(Integer val) { cancelledQuantity = val; }
	public String getCancelledReason() { return cancelledReason; }
	public void setCancelledReason(String val) { cancelledReason = val; }
	public String getCancelCode() { return cancelCode; }
	public void setCancelCode(String val) { cancelCode = val; }
	public Integer getAcceptedQuantity() { return acceptedQuantity; }
	public void setAcceptedQuantity(Integer val) { acceptedQuantity = val; }
	public String getAcceptedReason() { return acceptedReason; }
	public void setAcceptedReason(String val) { acceptedReason = val; }
	public Integer getRejectedQuantity() { return rejectedQuantity; }
	public void setRejectedQuantity(Integer val) { rejectedQuantity = val; }
	public String getRejectedReason() { return rejectedReason; }
	public void setRejectedReason(String val) { rejectedReason = val; }
	public Integer getLineNumber() { return lineNumber; }
	public void setLineNumber(Integer val) { lineNumber = val; }
	public String getMessage() { return message; }
	public void setMessage(String val) { message = val; }
	public String getPackingInstructions() { return packingInstructions; }
	public void setPackingInstructions(String val) { packingInstructions = val; }
	public String getShipInstructions() { return shipInstructions; }
	public void setShipInstructions(String val) { shipInstructions = val; }
	public String getReceiptId() { return receiptId; }
	public void setReceiptId(String val) { receiptId = val; }
	public Boolean getGiftFlag() { return giftFlag; }
	public void setGiftFlag(Boolean val) { giftFlag = val; }
	public String getGiftReceiptId() { return giftReceiptId; }
	public void setGiftReceiptId(String val) { giftReceiptId = val; }
	public String getGiftToName() { return giftToName; }
	public void setGiftToName(String val) { giftToName = val; }
	public String getGiftFromName() { return giftFromName; }
	public void setGiftFromName(String val) { giftFromName = val; }
	public String getGiftMessage() { return giftMessage; }
	public void setGiftMessage(String val) { giftMessage = val; }
	public String getColor() { return color; }
	public void setColor(String val) { color = val; }
	public String getSize() { return size; }
	public void setSize(String val) { size = val; }
	public Float getRetailPrice() { return retailPrice; }
	public void setRetailPrice(Float val) { retailPrice = val; }
	public Float getShippingSurcharge() { return shippingSurcharge; }
	public void setShippingSurcharge(Float val) { shippingSurcharge = val; }
	public List<OrderTax> getTaxes() { return taxes; }
	public void setTaxes(List<OrderTax> val) { taxes = val; }
	public List<OrderLineItemActivity> getActivity() { return activity; }
	public void setActivity(List<OrderLineItemActivity> val) { activity = val; }
	public List<String> getRetailerItemIds() { return retailerItemIds; }
	public void setRetailerItemIds(List<String> val) { retailerItemIds = val; }
	public String getDepartmentId() { return departmentId; }
	public void setDepartmentId(String val) { departmentId = val; }
	public String getDepartmentName() { return departmentName; }
	public void setDepartmentName(String val) { departmentName = val; }
	public String getMerchandisingAccountId() { return merchandisingAccountId; }
	public void setMerchandisingAccountId(String val) { merchandisingAccountId = val; }
	public String getMerchandisingAccountName() { return merchandisingAccountName; }
	public void setMerchandisingAccountName(String val) { merchandisingAccountName = val; }
	public Boolean getBogoFlag() { return bogoFlag; }
	public void setBogoFlag(Boolean val) { bogoFlag = val; }
	public String getBogoInstructions() { return bogoInstructions; }
	public void setBogoInstructions(String val) { bogoInstructions = val; }
	public Iso8601DateTime getUpdateDate() { return updateDate; }
	public void setUpdateDate(Iso8601DateTime val) { updateDate = val; }
}