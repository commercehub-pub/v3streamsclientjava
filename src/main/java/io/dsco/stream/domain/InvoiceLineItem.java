package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InvoiceLineItem {
	//ENUMS
	public enum SHIP_WEIGHT_UNITS {
		@SerializedName("lb") LB,
		@SerializedName("oz") OZ,
		@SerializedName("g") G,
		@SerializedName("kg") KG
	}

	//MEMBERS
	/* Dsco's unique ID for this item - one of the ID's must be present */
	private String dscoItemId;
	/* A unique ID for this item - one of the ID's must be present */
	private String sku;
	/* A unique ID for this item - one of the ID's must be present */
	private String upc;
	/* A unique ID for this item - one of the ID's must be present */
	private String ean;
	/* A unique ID for this item - one of the ID's must be present */
	private String mpn;
	/* Optional unique ID for this item */
	private String isbn;
	/* Optional unique ID for this item */
	private String gtin;
	/* A unique ID for this item - one of the ID's must be present */
	private String partnerSku;
	/* Uniquely identifies a line item for the order */
	private Integer lineNumber;
	private String title;
	private Float unitPrice;
	private String basisOfUnitPrice;
	/* The number of this item on the order */
	private int quantity;
	private String unitOfMeasure;
	private Float extendedAmount;
	private Float handlingAmount;
	private Iso8601DateTime shipDate;
	private String trackingNumber;
	private Float shipAmount;
	private String shipCarrier;
	private String shipMethod;
	private String shippingServiceLevelCode;
	private String promotionReference;
	private Float promotionAmount;
	private Float taxAmount;
	private Float subtotal;
	/* please use dscoExpectedAmount */
	private Float expectedAmount;
	/* please use dscoExpectedDifference */
	private Float expectedDifference;
	private Integer dscoPackageId;
	private Float shipWeight;
	private SHIP_WEIGHT_UNITS shipWeightUnits;
	private String warehouseCode;
	private String warehouseRetailerCode;
	private String warehouseDscoId;
	private String ssccBarcode;
	private Float dscoExpectedAmount;
	private Float dscoExpectedDifference;
	private Integer originalLineNumber;
	private Integer originalOrderQuantity;
	private String departmentId;
	private String departmentName;
	private String merchandisingAccountId;
	private String merchandisingAccountName;
	private Iso8601DateTime dscoOriginalOrderRetailerCreateDate;
	private String requestedWarehouseCode;
	private String requestedWarehouseRetailerCode;
	private String requestedWarehouseDscoId;
	private List<String> retailerItemIds;

	//CONSTRUCTORS
	public InvoiceLineItem() {}

	//ACCESSORS / MUTATORS
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
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public Integer getLineNumber() { return lineNumber; }
	public void setLineNumber(Integer val) { lineNumber = val; }
	public String getTitle() { return title; }
	public void setTitle(String val) { title = val; }
	public Float getUnitPrice() { return unitPrice; }
	public void setUnitPrice(Float val) { unitPrice = val; }
	public String getBasisOfUnitPrice() { return basisOfUnitPrice; }
	public void setBasisOfUnitPrice(String val) { basisOfUnitPrice = val; }
	public int getQuantity() { return quantity; }
	public void setQuantity(int val) { quantity = val; }
	public String getUnitOfMeasure() { return unitOfMeasure; }
	public void setUnitOfMeasure(String val) { unitOfMeasure = val; }
	public Float getExtendedAmount() { return extendedAmount; }
	public void setExtendedAmount(Float val) { extendedAmount = val; }
	public Float getHandlingAmount() { return handlingAmount; }
	public void setHandlingAmount(Float val) { handlingAmount = val; }
	public Iso8601DateTime getShipDate() { return shipDate; }
	public void setShipDate(Iso8601DateTime val) { shipDate = val; }
	public String getTrackingNumber() { return trackingNumber; }
	public void setTrackingNumber(String val) { trackingNumber = val; }
	public Float getShipAmount() { return shipAmount; }
	public void setShipAmount(Float val) { shipAmount = val; }
	public String getShipCarrier() { return shipCarrier; }
	public void setShipCarrier(String val) { shipCarrier = val; }
	public String getShipMethod() { return shipMethod; }
	public void setShipMethod(String val) { shipMethod = val; }
	public String getShippingServiceLevelCode() { return shippingServiceLevelCode; }
	public void setShippingServiceLevelCode(String val) { shippingServiceLevelCode = val; }
	public String getPromotionReference() { return promotionReference; }
	public void setPromotionReference(String val) { promotionReference = val; }
	public Float getPromotionAmount() { return promotionAmount; }
	public void setPromotionAmount(Float val) { promotionAmount = val; }
	public Float getTaxAmount() { return taxAmount; }
	public void setTaxAmount(Float val) { taxAmount = val; }
	public Float getSubtotal() { return subtotal; }
	public void setSubtotal(Float val) { subtotal = val; }
	public Float getExpectedAmount() { return expectedAmount; }
	public void setExpectedAmount(Float val) { expectedAmount = val; }
	public Float getExpectedDifference() { return expectedDifference; }
	public void setExpectedDifference(Float val) { expectedDifference = val; }
	public Integer getDscoPackageId() { return dscoPackageId; }
	public void setDscoPackageId(Integer val) { dscoPackageId = val; }
	public Float getShipWeight() { return shipWeight; }
	public void setShipWeight(Float val) { shipWeight = val; }
	public SHIP_WEIGHT_UNITS getShipWeightUnits() { return shipWeightUnits; }
	public void setShipWeightUnits(SHIP_WEIGHT_UNITS val) { shipWeightUnits = val; }
	public String getWarehouseCode() { return warehouseCode; }
	public void setWarehouseCode(String val) { warehouseCode = val; }
	public String getWarehouseRetailerCode() { return warehouseRetailerCode; }
	public void setWarehouseRetailerCode(String val) { warehouseRetailerCode = val; }
	public String getWarehouseDscoId() { return warehouseDscoId; }
	public void setWarehouseDscoId(String val) { warehouseDscoId = val; }
	public String getSsccBarcode() { return ssccBarcode; }
	public void setSsccBarcode(String val) { ssccBarcode = val; }
	public Float getDscoExpectedAmount() { return dscoExpectedAmount; }
	public void setDscoExpectedAmount(Float val) { dscoExpectedAmount = val; }
	public Float getDscoExpectedDifference() { return dscoExpectedDifference; }
	public void setDscoExpectedDifference(Float val) { dscoExpectedDifference = val; }
	public Integer getOriginalLineNumber() { return originalLineNumber; }
	public void setOriginalLineNumber(Integer val) { originalLineNumber = val; }
	public Integer getOriginalOrderQuantity() { return originalOrderQuantity; }
	public void setOriginalOrderQuantity(Integer val) { originalOrderQuantity = val; }
	public String getDepartmentId() { return departmentId; }
	public void setDepartmentId(String val) { departmentId = val; }
	public String getDepartmentName() { return departmentName; }
	public void setDepartmentName(String val) { departmentName = val; }
	public String getMerchandisingAccountId() { return merchandisingAccountId; }
	public void setMerchandisingAccountId(String val) { merchandisingAccountId = val; }
	public String getMerchandisingAccountName() { return merchandisingAccountName; }
	public void setMerchandisingAccountName(String val) { merchandisingAccountName = val; }
	public Iso8601DateTime getDscoOriginalOrderRetailerCreateDate() { return dscoOriginalOrderRetailerCreateDate; }
	public void setDscoOriginalOrderRetailerCreateDate(Iso8601DateTime val) { dscoOriginalOrderRetailerCreateDate = val; }
	public String getRequestedWarehouseCode() { return requestedWarehouseCode; }
	public void setRequestedWarehouseCode(String val) { requestedWarehouseCode = val; }
	public String getRequestedWarehouseRetailerCode() { return requestedWarehouseRetailerCode; }
	public void setRequestedWarehouseRetailerCode(String val) { requestedWarehouseRetailerCode = val; }
	public String getRequestedWarehouseDscoId() { return requestedWarehouseDscoId; }
	public void setRequestedWarehouseDscoId(String val) { requestedWarehouseDscoId = val; }
	public List<String> getRetailerItemIds() { return retailerItemIds; }
	public void setRetailerItemIds(List<String> val) { retailerItemIds = val; }
}