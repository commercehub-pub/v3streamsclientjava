package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InvoiceShipInfo {
	//ENUMS
	public enum WEIGHT_UNITS {
		@SerializedName("lb") LB,
		@SerializedName("oz") OZ,
		@SerializedName("g") G,
		@SerializedName("kg") KG
	}

	//MEMBERS
	private Integer numberOfUnitsShipped;
	private String unitOfMeasurementCode;
	private Float weight;
	private WEIGHT_UNITS weightUnits;
	private Iso8601DateTime date;
	private String trackingNumber;
	private String carrier;
	private String method;
	private String serviceLevelCode;
	private String transportationMethodCode;
	private String referenceNumberQualifier;
	private Integer dscoPackageId;
	private String warehouseCode;
	private String warehouseRetailerCode;
	private String warehouseDscoId;
	private String ssccBarcode;

	//CONSTRUCTORS
	public InvoiceShipInfo() {}

	//ACCESSORS / MUTATORS
	public Integer getNumberOfUnitsShipped() { return numberOfUnitsShipped; }
	public void setNumberOfUnitsShipped(Integer val) { numberOfUnitsShipped = val; }
	public String getUnitOfMeasurementCode() { return unitOfMeasurementCode; }
	public void setUnitOfMeasurementCode(String val) { unitOfMeasurementCode = val; }
	public Float getWeight() { return weight; }
	public void setWeight(Float val) { weight = val; }
	public WEIGHT_UNITS getWeightUnits() { return weightUnits; }
	public void setWeightUnits(WEIGHT_UNITS val) { weightUnits = val; }
	public Iso8601DateTime getDate() { return date; }
	public void setDate(Iso8601DateTime val) { date = val; }
	public String getTrackingNumber() { return trackingNumber; }
	public void setTrackingNumber(String val) { trackingNumber = val; }
	public String getCarrier() { return carrier; }
	public void setCarrier(String val) { carrier = val; }
	public String getMethod() { return method; }
	public void setMethod(String val) { method = val; }
	public String getServiceLevelCode() { return serviceLevelCode; }
	public void setServiceLevelCode(String val) { serviceLevelCode = val; }
	public String getTransportationMethodCode() { return transportationMethodCode; }
	public void setTransportationMethodCode(String val) { transportationMethodCode = val; }
	public String getReferenceNumberQualifier() { return referenceNumberQualifier; }
	public void setReferenceNumberQualifier(String val) { referenceNumberQualifier = val; }
	public Integer getDscoPackageId() { return dscoPackageId; }
	public void setDscoPackageId(Integer val) { dscoPackageId = val; }
	public String getWarehouseCode() { return warehouseCode; }
	public void setWarehouseCode(String val) { warehouseCode = val; }
	public String getWarehouseRetailerCode() { return warehouseRetailerCode; }
	public void setWarehouseRetailerCode(String val) { warehouseRetailerCode = val; }
	public String getWarehouseDscoId() { return warehouseDscoId; }
	public void setWarehouseDscoId(String val) { warehouseDscoId = val; }
	public String getSsccBarcode() { return ssccBarcode; }
	public void setSsccBarcode(String val) { ssccBarcode = val; }
}