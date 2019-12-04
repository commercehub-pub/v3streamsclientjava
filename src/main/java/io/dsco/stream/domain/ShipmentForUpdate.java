package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShipmentForUpdate
{
    private List<ShipmentLineItemForUpdate> lineItems;
    private String trackingNumber;
    private String currencyCode;
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

    public ShipmentForUpdate(
            @NotNull List<ShipmentLineItemForUpdate> lineItems, @NotNull String trackingNumber,
             String currencyCode, String shipCarrier, Float shipCost, String shipDate, String shipMethod,
             String shippingServiceLevelCode, Float shipWeight, String shipWeightUnits, String ssccBarcode,
             String warehouseCode, String warehouseDscoId, String warehouseRetailerCode)
    {
        this.lineItems = lineItems;
        this.trackingNumber = trackingNumber;
        this.currencyCode = currencyCode;
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

    public List<ShipmentLineItemForUpdate> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<ShipmentLineItemForUpdate> lineItems)
    {
        this.lineItems = lineItems;
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
