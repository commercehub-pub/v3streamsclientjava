package io.dsco.stream.domain;

public class InvoiceShipInfo
{
    public String carrier;
    public String date; //iso8601
    public Integer dscoShipmentId;
    public String method;
    public Integer numberOfUnitsShipped;
    public String referenceNumberQualifier;
    public String serviceLevelCode;
    public String ssccBarcode;
    public String trackingNumber;
    public String transportationMethodCode;
    public String unitOfMeasurementCode;
    public String warehouseCode;
    public String warehouseDscoId;
    public String warehouseRetailerCode;
    public Float weight;
    public String weightUnits;

    public String getCarrier()
    {
        return carrier;
    }

    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Integer getDscoShipmentId()
    {
        return dscoShipmentId;
    }

    public void setDscoShipmentId(Integer dscoShipmentId)
    {
        this.dscoShipmentId = dscoShipmentId;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public Integer getNumberOfUnitsShipped()
    {
        return numberOfUnitsShipped;
    }

    public void setNumberOfUnitsShipped(Integer numberOfUnitsShipped)
    {
        this.numberOfUnitsShipped = numberOfUnitsShipped;
    }

    public String getReferenceNumberQualifier()
    {
        return referenceNumberQualifier;
    }

    public void setReferenceNumberQualifier(String referenceNumberQualifier)
    {
        this.referenceNumberQualifier = referenceNumberQualifier;
    }

    public String getServiceLevelCode()
    {
        return serviceLevelCode;
    }

    public void setServiceLevelCode(String serviceLevelCode)
    {
        this.serviceLevelCode = serviceLevelCode;
    }

    public String getSsccBarcode()
    {
        return ssccBarcode;
    }

    public void setSsccBarcode(String ssccBarcode)
    {
        this.ssccBarcode = ssccBarcode;
    }

    public String getTrackingNumber()
    {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber)
    {
        this.trackingNumber = trackingNumber;
    }

    public String getTransportationMethodCode()
    {
        return transportationMethodCode;
    }

    public void setTransportationMethodCode(String transportationMethodCode)
    {
        this.transportationMethodCode = transportationMethodCode;
    }

    public String getUnitOfMeasurementCode()
    {
        return unitOfMeasurementCode;
    }

    public void setUnitOfMeasurementCode(String unitOfMeasurementCode)
    {
        this.unitOfMeasurementCode = unitOfMeasurementCode;
    }

    public String getWarehouseCode()
    {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode)
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseDscoId()
    {
        return warehouseDscoId;
    }

    public void setWarehouseDscoId(String warehouseDscoId)
    {
        this.warehouseDscoId = warehouseDscoId;
    }

    public String getWarehouseRetailerCode()
    {
        return warehouseRetailerCode;
    }

    public void setWarehouseRetailerCode(String warehouseRetailerCode)
    {
        this.warehouseRetailerCode = warehouseRetailerCode;
    }

    public Float getWeight()
    {
        return weight;
    }

    public void setWeight(Float weight)
    {
        this.weight = weight;
    }

    public String getWeightUnits()
    {
        return weightUnits;
    }

    public void setWeightUnits(String weightUnits)
    {
        this.weightUnits = weightUnits;
    }
}
