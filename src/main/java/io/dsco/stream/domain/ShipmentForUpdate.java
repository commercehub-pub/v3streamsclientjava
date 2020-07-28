package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class ShipmentForUpdate {
	//MEMBERS
	private List<ShipmentLineItemForUpdate> lineItems;
	/* The PackageShipFrom JSON object defines the ship to for a Package in the DSCO system. */
	private PackageShipFrom shipFrom;
	/* The PackageShipTo JSON object defines the ship to for a Package in the DSCO system. */
	private PackageShipTo shipTo;
	/* the tracking number of the shipment */
	private String trackingNumber;
	private Float shipCost;
	/* the date the shipment shipped */
	private Iso8601DateTime shipDate;
	private String currencyCode;
	private String shipCarrier;
	private String shipMethod;
	private String shippingServiceLevelCode;
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
	public ShipmentForUpdate() {}

	//ACCESSORS / MUTATORS
	public List<ShipmentLineItemForUpdate> getLineItems() { return lineItems; }
	public void setLineItems(List<ShipmentLineItemForUpdate> val) { lineItems = val; }
	public PackageShipFrom getShipFrom() { return shipFrom; }
	public void setShipFrom(PackageShipFrom val) { shipFrom = val; }
	public PackageShipTo getShipTo() { return shipTo; }
	public void setShipTo(PackageShipTo val) { shipTo = val; }
	public String getTrackingNumber() { return trackingNumber; }
	public void setTrackingNumber(String val) { trackingNumber = val; }
	public Float getShipCost() { return shipCost; }
	public void setShipCost(Float val) { shipCost = val; }
	public Iso8601DateTime getShipDate() { return shipDate; }
	public void setShipDate(Iso8601DateTime val) { shipDate = val; }
	public String getCurrencyCode() { return currencyCode; }
	public void setCurrencyCode(String val) { currencyCode = val; }
	public String getShipCarrier() { return shipCarrier; }
	public void setShipCarrier(String val) { shipCarrier = val; }
	public String getShipMethod() { return shipMethod; }
	public void setShipMethod(String val) { shipMethod = val; }
	public String getShippingServiceLevelCode() { return shippingServiceLevelCode; }
	public void setShippingServiceLevelCode(String val) { shippingServiceLevelCode = val; }
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