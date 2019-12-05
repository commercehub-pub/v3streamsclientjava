package io.dsco.stream.domain;

public class InvoiceLineItemForUpdate
{
    public int quantity; //TODO:
    public String basisOfUnitPrice;
    public String dscoItemId;
    public String dscoShipmentId;
    public String ean;
    public Float expectedAmount;
    public Float expectedDifference;
    public Float extendedAmount;
    public Float handlingAmount;
    public Integer lineNumber;
    public String partnerSku;
    public Float promotionAmount;
    public String promotionReference;
    public Float shipAmount;
    public String shipCarrier;
    public String shipDate; //iso8601
    public String shipMethod;
    public String shippingServiceLevelCode; //TODO: use this: UPCG
    public Float shipWeight;
    public String shipWeightUnits;
    public String sku;
    public String ssccBarcode;
    public Float subtotal;
    public Float taxAmount;
    public String trackingNumber; //TODO: this
    public String unitOfMeasure;
    public Float unitPrice; //TODO: this
    public String upc;
    public String warehouseCode;
    public String warehouseDscoId;
    public String warehouseRetailerCode;

    public InvoiceLineItemForUpdate() {}

    public InvoiceLineItemForUpdate(
            int quantity, String dscoItemId, String ean, String partnerSku, String sku, String upc,
            float retailerPrice)
    {
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the unique id columns must be present");
        }

        this.quantity = quantity;
        this.dscoItemId = dscoItemId;
        this.ean = ean;
        this.partnerSku = partnerSku;
        this.sku = sku;
        this.upc = upc;
        this.unitPrice = retailerPrice;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getBasisOfUnitPrice()
    {
        return basisOfUnitPrice;
    }

    public void setBasisOfUnitPrice(String basisOfUnitPrice)
    {
        this.basisOfUnitPrice = basisOfUnitPrice;
    }

    public String getDscoItemId()
    {
        return dscoItemId;
    }

    public void setDscoItemId(String dscoItemId)
    {
        this.dscoItemId = dscoItemId;
    }

    public String getDscoShipmentId()
    {
        return dscoShipmentId;
    }

    public void setDscoShipmentId(String dscoShipmentId)
    {
        this.dscoShipmentId = dscoShipmentId;
    }

    public String getEan()
    {
        return ean;
    }

    public void setEan(String ean)
    {
        this.ean = ean;
    }

    public Float getExpectedAmount()
    {
        return expectedAmount;
    }

    public void setExpectedAmount(Float expectedAmount)
    {
        this.expectedAmount = expectedAmount;
    }

    public Float getExpectedDifference()
    {
        return expectedDifference;
    }

    public void setExpectedDifference(Float expectedDifference)
    {
        this.expectedDifference = expectedDifference;
    }

    public Float getExtendedAmount()
    {
        return extendedAmount;
    }

    public void setExtendedAmount(Float extendedAmount)
    {
        this.extendedAmount = extendedAmount;
    }

    public Float getHandlingAmount()
    {
        return handlingAmount;
    }

    public void setHandlingAmount(Float handlingAmount)
    {
        this.handlingAmount = handlingAmount;
    }

    public Integer getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public Float getPromotionAmount()
    {
        return promotionAmount;
    }

    public void setPromotionAmount(Float promotionAmount)
    {
        this.promotionAmount = promotionAmount;
    }

    public String getPromotionReference()
    {
        return promotionReference;
    }

    public void setPromotionReference(String promotionReference)
    {
        this.promotionReference = promotionReference;
    }

    public Float getShipAmount()
    {
        return shipAmount;
    }

    public void setShipAmount(Float shipAmount)
    {
        this.shipAmount = shipAmount;
    }

    public String getShipCarrier()
    {
        return shipCarrier;
    }

    public void setShipCarrier(String shipCarrier)
    {
        this.shipCarrier = shipCarrier;
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

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getSsccBarcode()
    {
        return ssccBarcode;
    }

    public void setSsccBarcode(String ssccBarcode)
    {
        this.ssccBarcode = ssccBarcode;
    }

    public Float getSubtotal()
    {
        return subtotal;
    }

    public void setSubtotal(Float subtotal)
    {
        this.subtotal = subtotal;
    }

    public Float getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public String getTrackingNumber()
    {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber)
    {
        this.trackingNumber = trackingNumber;
    }

    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Float getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public String getUpc()
    {
        return upc;
    }

    public void setUpc(String upc)
    {
        this.upc = upc;
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
