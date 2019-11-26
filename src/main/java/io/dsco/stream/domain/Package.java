package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Package
{
    private List<PackageLineItem> items;
    private String trackingNumber;
    private String currencyCode;
    private String dscoActualDeliveryDate; //iso8601
    private String dscoActualPickupDate; //iso8601
    private String dscoActualShipCarrier;
    private Float dscoActualShipCost;
    private String dscoActualShipMethod;
    private String dscoActualShippingServiceLevelCode;
    private Integer dscoPackageId;
    private Integer numberOfLineItems;
    private PackageShipFrom packageShipFrom;
    private PackageShipTo packageShipTo;
    private String returnDate; //iso8601
    private Boolean returnedFlag;
    private String returnNumber;
    private String returnReason;
    private String shipCarrier;
    private Float shipCost;
    private String shipDate; //iso8601
    private String shipMethod;
    private String shippingServiceLevelCode;
    private Float shipWeight;
    private String shipWeightUnits;
    private String ssccBarcode;
    private String warehouseCode;
    private String warehouseDscoId;
    private String warehouseRetailerCode;

    public Package(
            @NotNull List<PackageLineItem> items, @NotNull String trackingNumber, String currencyCode,
            @NotNull String dscoActualDeliveryDate, @NotNull String dscoActualPickupDate, String dscoActualShipCarrier,
            Float dscoActualShipCost, String dscoActualShipMethod, String dscoActualShippingServiceLevelCode,
            Integer dscoPackageId, Integer numberOfLineItems, @NotNull PackageShipFrom packageShipFrom,
            @NotNull PackageShipTo packageShipTo, String returnDate, Boolean returnedFlag, String returnNumber,
            String returnReason, String shipCarrier, Float shipCost, String shipDate, String shipMethod,
            String shippingServiceLevelCode, Float shipWeight, String shipWeightUnits, String ssccBarcode,
            String warehouseCode, String warehouseDscoId, String warehouseRetailerCode)
    {
        this.items = items;
        this.trackingNumber = trackingNumber;
        this.currencyCode = currencyCode;
        this.dscoActualDeliveryDate = dscoActualDeliveryDate;
        this.dscoActualPickupDate = dscoActualPickupDate;
        this.dscoActualShipCarrier = dscoActualShipCarrier;
        this.dscoActualShipCost = dscoActualShipCost;
        this.dscoActualShipMethod = dscoActualShipMethod;
        this.dscoActualShippingServiceLevelCode = dscoActualShippingServiceLevelCode;
        this.dscoPackageId = dscoPackageId;
        this.numberOfLineItems = numberOfLineItems;
        this.packageShipFrom = packageShipFrom;
        this.packageShipTo = packageShipTo;
        this.returnDate = returnDate;
        this.returnedFlag = returnedFlag;
        this.returnNumber = returnNumber;
        this.returnReason = returnReason;
        this.shipCarrier = shipCarrier;
        this.shipCost = shipCost;
        this.shipDate = shipDate;
        this.shipMethod = shipMethod;
        this.shippingServiceLevelCode = shippingServiceLevelCode;
        this.shipWeight = shipWeight;
        this.shipWeightUnits = shipWeightUnits;
        this.ssccBarcode = ssccBarcode;
        this.warehouseCode = warehouseCode;
        this.warehouseDscoId = warehouseDscoId;
        this.warehouseRetailerCode = warehouseRetailerCode;
    }

    public List<PackageLineItem> getItems()
    {
        return items;
    }

    public void setItems(List<PackageLineItem> items)
    {
        this.items = items;
    }

    public String getTrackingNumber()
    {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber)
    {
        this.trackingNumber = trackingNumber;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getDscoActualDeliveryDate()
    {
        return dscoActualDeliveryDate;
    }

    public void setDscoActualDeliveryDate(String dscoActualDeliveryDate)
    {
        this.dscoActualDeliveryDate = dscoActualDeliveryDate;
    }

    public String getDscoActualPickupDate()
    {
        return dscoActualPickupDate;
    }

    public void setDscoActualPickupDate(String dscoActualPickupDate)
    {
        this.dscoActualPickupDate = dscoActualPickupDate;
    }

    public String getDscoActualShipCarrier()
    {
        return dscoActualShipCarrier;
    }

    public void setDscoActualShipCarrier(String dscoActualShipCarrier)
    {
        this.dscoActualShipCarrier = dscoActualShipCarrier;
    }

    public Float getDscoActualShipCost()
    {
        return dscoActualShipCost;
    }

    public void setDscoActualShipCost(Float dscoActualShipCost)
    {
        this.dscoActualShipCost = dscoActualShipCost;
    }

    public String getDscoActualShipMethod()
    {
        return dscoActualShipMethod;
    }

    public void setDscoActualShipMethod(String dscoActualShipMethod)
    {
        this.dscoActualShipMethod = dscoActualShipMethod;
    }

    public String getDscoActualShippingServiceLevelCode()
    {
        return dscoActualShippingServiceLevelCode;
    }

    public void setDscoActualShippingServiceLevelCode(String dscoActualShippingServiceLevelCode)
    {
        this.dscoActualShippingServiceLevelCode = dscoActualShippingServiceLevelCode;
    }

    public Integer getDscoPackageId()
    {
        return dscoPackageId;
    }

    public void setDscoPackageId(Integer dscoPackageId)
    {
        this.dscoPackageId = dscoPackageId;
    }

    public Integer getNumberOfLineItems()
    {
        return numberOfLineItems;
    }

    public void setNumberOfLineItems(Integer numberOfLineItems)
    {
        this.numberOfLineItems = numberOfLineItems;
    }

    public PackageShipFrom getPackageShipFrom()
    {
        return packageShipFrom;
    }

    public void setPackageShipFrom(PackageShipFrom packageShipFrom)
    {
        this.packageShipFrom = packageShipFrom;
    }

    public PackageShipTo getPackageShipTo()
    {
        return packageShipTo;
    }

    public void setPackageShipTo(PackageShipTo packageShipTo)
    {
        this.packageShipTo = packageShipTo;
    }

    public String getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(String returnDate)
    {
        this.returnDate = returnDate;
    }

    public Boolean getReturnedFlag()
    {
        return returnedFlag;
    }

    public void setReturnedFlag(Boolean returnedFlag)
    {
        this.returnedFlag = returnedFlag;
    }

    public String getReturnNumber()
    {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber)
    {
        this.returnNumber = returnNumber;
    }

    public String getReturnReason()
    {
        return returnReason;
    }

    public void setReturnReason(String returnReason)
    {
        this.returnReason = returnReason;
    }

    public String getShipCarrier()
    {
        return shipCarrier;
    }

    public void setShipCarrier(String shipCarrier)
    {
        this.shipCarrier = shipCarrier;
    }

    public Float getShipCost()
    {
        return shipCost;
    }

    public void setShipCost(Float shipCost)
    {
        this.shipCost = shipCost;
    }

    public String getShipDate()
    {
        return shipDate;
    }

    public void setShipDate(String shipDate)
    {
        this.shipDate = shipDate;
    }

    public String getShipMethod()
    {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod)
    {
        this.shipMethod = shipMethod;
    }

    public String getShippingServiceLevelCode()
    {
        return shippingServiceLevelCode;
    }

    public void setShippingServiceLevelCode(String shippingServiceLevelCode)
    {
        this.shippingServiceLevelCode = shippingServiceLevelCode;
    }

    public Float getShipWeight()
    {
        return shipWeight;
    }

    public void setShipWeight(Float shipWeight)
    {
        this.shipWeight = shipWeight;
    }

    public String getShipWeightUnits()
    {
        return shipWeightUnits;
    }

    public void setShipWeightUnits(String shipWeightUnits)
    {
        this.shipWeightUnits = shipWeightUnits;
    }

    public String getSsccBarcode()
    {
        return ssccBarcode;
    }

    public void setSsccBarcode(String ssccBarcode)
    {
        this.ssccBarcode = ssccBarcode;
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
}
