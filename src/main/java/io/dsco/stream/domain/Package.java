package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class Package {
	//MEMBERS
	private String trackingNumber;
	private Float shipCost;
	/* When the retailer created the order */
	private Iso8601DateTime shipDate;
	private String shipCarrier;
	private String shipMethod;
	private List<PackageLineItem> items;
	private String currencyCode;
	private String shippingServiceLevelCode;
	private String dscoActualShipMethod;
	private String dscoActualShipCarrier;
	private String dscoActualShippingServiceLevelCode;
	private Float dscoActualShipCost;
	private Iso8601DateTime dscoActualDeliveryDate;
	private Iso8601DateTime dscoActualPickupDate;
	private Boolean returnedFlag;
	private Iso8601DateTime returnDate;
	private String returnNumber;
	private String returnReason;
	private Integer numberOfLineItems;
	/* The PackageShipFrom JSON object defines the ship to for a Package in the DSCO system. */
	private PackageShipFrom packageShipFrom;
	/* The PackageShipTo JSON object defines the ship to for a Package in the DSCO system. */
	private PackageShipTo packageShipTo;
	private Integer dscoPackageId;
	private Float shipWeight;
	private String shipWeightUnits;
	/* the supplier ID for warehouse */
	private String warehouseCode;
	/* the retailer ID for warehouse */
	private String warehouseRetailerCode;
	/* Dsco's ID for warehouse */
	private String warehouseDscoId;
	private String ssccBarcode;

	//CONSTRUCTORS
	public Package() {}

	//ACCESSORS / MUTATORS
	public String getTrackingNumber() { return trackingNumber; }
	public void setTrackingNumber(String val) { trackingNumber = val; }
	public Float getShipCost() { return shipCost; }
	public void setShipCost(Float val) { shipCost = val; }
	public Iso8601DateTime getShipDate() { return shipDate; }
	public void setShipDate(Iso8601DateTime val) { shipDate = val; }
	public String getShipCarrier() { return shipCarrier; }
	public void setShipCarrier(String val) { shipCarrier = val; }
	public String getShipMethod() { return shipMethod; }
	public void setShipMethod(String val) { shipMethod = val; }
	public List<PackageLineItem> getItems() { return items; }
	public void setItems(List<PackageLineItem> val) { items = val; }
	public String getCurrencyCode() { return currencyCode; }
	public void setCurrencyCode(String val) { currencyCode = val; }
	public String getShippingServiceLevelCode() { return shippingServiceLevelCode; }
	public void setShippingServiceLevelCode(String val) { shippingServiceLevelCode = val; }
	public String getDscoActualShipMethod() { return dscoActualShipMethod; }
	public void setDscoActualShipMethod(String val) { dscoActualShipMethod = val; }
	public String getDscoActualShipCarrier() { return dscoActualShipCarrier; }
	public void setDscoActualShipCarrier(String val) { dscoActualShipCarrier = val; }
	public String getDscoActualShippingServiceLevelCode() { return dscoActualShippingServiceLevelCode; }
	public void setDscoActualShippingServiceLevelCode(String val) { dscoActualShippingServiceLevelCode = val; }
	public Float getDscoActualShipCost() { return dscoActualShipCost; }
	public void setDscoActualShipCost(Float val) { dscoActualShipCost = val; }
	public Iso8601DateTime getDscoActualDeliveryDate() { return dscoActualDeliveryDate; }
	public void setDscoActualDeliveryDate(Iso8601DateTime val) { dscoActualDeliveryDate = val; }
	public Iso8601DateTime getDscoActualPickupDate() { return dscoActualPickupDate; }
	public void setDscoActualPickupDate(Iso8601DateTime val) { dscoActualPickupDate = val; }
	public Boolean getReturnedFlag() { return returnedFlag; }
	public void setReturnedFlag(Boolean val) { returnedFlag = val; }
	public Iso8601DateTime getReturnDate() { return returnDate; }
	public void setReturnDate(Iso8601DateTime val) { returnDate = val; }
	public String getReturnNumber() { return returnNumber; }
	public void setReturnNumber(String val) { returnNumber = val; }
	public String getReturnReason() { return returnReason; }
	public void setReturnReason(String val) { returnReason = val; }
	public Integer getNumberOfLineItems() { return numberOfLineItems; }
	public void setNumberOfLineItems(Integer val) { numberOfLineItems = val; }
	public PackageShipFrom getPackageShipFrom() { return packageShipFrom; }
	public void setPackageShipFrom(PackageShipFrom val) { packageShipFrom = val; }
	public PackageShipTo getPackageShipTo() { return packageShipTo; }
	public void setPackageShipTo(PackageShipTo val) { packageShipTo = val; }
	public Integer getDscoPackageId() { return dscoPackageId; }
	public void setDscoPackageId(Integer val) { dscoPackageId = val; }
	public Float getShipWeight() { return shipWeight; }
	public void setShipWeight(Float val) { shipWeight = val; }
	public String getShipWeightUnits() { return shipWeightUnits; }
	public void setShipWeightUnits(String val) { shipWeightUnits = val; }
	public String getWarehouseCode() { return warehouseCode; }
	public void setWarehouseCode(String val) { warehouseCode = val; }
	public String getWarehouseRetailerCode() { return warehouseRetailerCode; }
	public void setWarehouseRetailerCode(String val) { warehouseRetailerCode = val; }
	public String getWarehouseDscoId() { return warehouseDscoId; }
	public void setWarehouseDscoId(String val) { warehouseDscoId = val; }
	public String getSsccBarcode() { return ssccBarcode; }
	public void setSsccBarcode(String val) { ssccBarcode = val; }
}