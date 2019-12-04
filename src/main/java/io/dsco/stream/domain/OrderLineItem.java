package io.dsco.stream.domain;

import java.util.List;

public class OrderLineItem
{
    public enum Status {accepted, rejected, cancelled}

    private int quantity;
    private Integer acceptedQuantity;
    private String acceptedReason;
    private List<OrderLineItemActivity> activity;
    private Integer cancelledQuantity;
    private String cancelledReason;
    private String color;
    private Float consumerPrice;
    private String departmentId;
    private String departmentName;
    private String dscoItemId;
    private String dscoSupplierId;
    private String dscoTradingPartnerId;
    private String ean;
    private Float expectedCost;
    private Boolean giftFlag;
    private String giftFromName;
    private String giftMessage;
    private String giftReceiptId;
    private String giftToName;
    private Integer lineNumber;
    private String merchandisingAccountId;
    private String merchandisingAccountName;
    private String message;
    private String packingInstructions;
    private String partnerSku;
    private String personalization;
    private String receiptId;
    private Integer rejectedQuantity;
    private String rejectedReason;
    private List<String> retailerItemIds;
    private Float retailerPrice;
    private String shipInstructions;
    private Float shippingSurcharge;
    private Integer size;
    private String sku;
    private Status status;
    private String statusReason;
    private List<OrderTax> taxes;
    private String title;
    private String upc;
    private String warehouseCode;
    private String warehouseDscoId;
    private String warehouseRetailerCode;

    public OrderLineItem() {}

    public OrderLineItem(int quantity, int lineNumber, String dscoItemId, String ean, String partnerSku, String sku, String upc)
    {
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("at least one of the unique id fields must be present");
        }

        this.quantity = quantity;
        this.lineNumber = lineNumber;
        this.dscoItemId = dscoItemId;
        this.ean = ean;
        this.partnerSku = partnerSku;
        this.sku = sku;
        this.upc = upc;
    }

    public OrderLineItem(
            int quantity, Integer acceptedQuantity, String acceptedReason, List<OrderLineItemActivity> activity, Integer cancelledQuantity,
            String cancelledReason, String color, Float consumerPrice, String departmentId, String departmentName, String dscoItemId,
            String dscoSupplierId, String dscoTradingPartnerId, String ean, Float expectedCost, Boolean giftFlag, String giftFromName,
            String giftMessage, String giftReceiptId, String giftToName, Integer lineNumber, String merchandisingAccountId,
            String merchandisingAccountName, String message, String packingInstructions, String partnerSku, String personalization,
            String receiptId, Integer rejectedQuantity, String rejectedReason, List<String> retailerItemIds, Float retailerPrice,
            String shipInstructions, Float shippingSurcharge, Integer size, String sku, Status status, String statusReason,
            List<OrderTax> taxes, String title, String upc, String warehouseCode, String warehouseDscoId, String warehouseRetailerCode)
    {
        if (dscoItemId == null && /*dscoSupplierId == null && dscoTradingPartnerId == null &&*/ ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("at least one of the unique id fields must be present");
        }

        this.quantity = quantity;
        this.acceptedQuantity = acceptedQuantity;
        this.acceptedReason = acceptedReason;
        this.activity = activity;
        this.cancelledQuantity = cancelledQuantity;
        this.cancelledReason = cancelledReason;
        this.color = color;
        this.consumerPrice = consumerPrice;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.dscoItemId = dscoItemId;
        this.dscoSupplierId = dscoSupplierId;
        this.dscoTradingPartnerId = dscoTradingPartnerId;
        this.ean = ean;
        this.expectedCost = expectedCost;
        this.giftFlag = giftFlag;
        this.giftFromName = giftFromName;
        this.giftMessage = giftMessage;
        this.giftReceiptId = giftReceiptId;
        this.giftToName = giftToName;
        this.lineNumber = lineNumber;
        this.merchandisingAccountId = merchandisingAccountId;
        this.merchandisingAccountName = merchandisingAccountName;
        this.message = message;
        this.packingInstructions = packingInstructions;
        this.partnerSku = partnerSku;
        this.personalization = personalization;
        this.receiptId = receiptId;
        this.rejectedQuantity = rejectedQuantity;
        this.rejectedReason = rejectedReason;
        this.retailerItemIds = retailerItemIds;
        this.retailerPrice = retailerPrice;
        this.shipInstructions = shipInstructions;
        this.shippingSurcharge = shippingSurcharge;
        this.size = size;
        this.sku = sku;
        this.status = status;
        this.statusReason = statusReason;
        this.taxes = taxes;
        this.title = title;
        this.upc = upc;
        this.warehouseCode = warehouseCode;
        this.warehouseDscoId = warehouseDscoId;
        this.warehouseRetailerCode = warehouseRetailerCode;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Integer getAcceptedQuantity()
    {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(Integer acceptedQuantity)
    {
        this.acceptedQuantity = acceptedQuantity;
    }

    public String getAcceptedReason()
    {
        return acceptedReason;
    }

    public void setAcceptedReason(String acceptedReason)
    {
        this.acceptedReason = acceptedReason;
    }

    public List<OrderLineItemActivity> getActivity()
    {
        return activity;
    }

    public void setActivity(List<OrderLineItemActivity> activity)
    {
        this.activity = activity;
    }

    public Integer getCancelledQuantity()
    {
        return cancelledQuantity;
    }

    public void setCancelledQuantity(Integer cancelledQuantity)
    {
        this.cancelledQuantity = cancelledQuantity;
    }

    public String getCancelledReason()
    {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason)
    {
        this.cancelledReason = cancelledReason;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Float getConsumerPrice()
    {
        return consumerPrice;
    }

    public void setConsumerPrice(Float consumerPrice)
    {
        this.consumerPrice = consumerPrice;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getDscoItemId()
    {
        return dscoItemId;
    }

    public void setDscoItemId(String dscoItemId)
    {
        this.dscoItemId = dscoItemId;
    }

    public String getDscoSupplierId()
    {
        return dscoSupplierId;
    }

    public void setDscoSupplierId(String dscoSupplierId)
    {
        this.dscoSupplierId = dscoSupplierId;
    }

    public String getDscoTradingPartnerId()
    {
        return dscoTradingPartnerId;
    }

    public void setDscoTradingPartnerId(String dscoTradingPartnerId)
    {
        this.dscoTradingPartnerId = dscoTradingPartnerId;
    }

    public String getEan()
    {
        return ean;
    }

    public void setEan(String ean)
    {
        this.ean = ean;
    }

    public Float getExpectedCost()
    {
        return expectedCost;
    }

    public void setExpectedCost(Float expectedCost)
    {
        this.expectedCost = expectedCost;
    }

    public Boolean getGiftFlag()
    {
        return giftFlag;
    }

    public void setGiftFlag(Boolean giftFlag)
    {
        this.giftFlag = giftFlag;
    }

    public String getGiftFromName()
    {
        return giftFromName;
    }

    public void setGiftFromName(String giftFromName)
    {
        this.giftFromName = giftFromName;
    }

    public String getGiftMessage()
    {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage)
    {
        this.giftMessage = giftMessage;
    }

    public String getGiftReceiptId()
    {
        return giftReceiptId;
    }

    public void setGiftReceiptId(String giftReceiptId)
    {
        this.giftReceiptId = giftReceiptId;
    }

    public String getGiftToName()
    {
        return giftToName;
    }

    public void setGiftToName(String giftToName)
    {
        this.giftToName = giftToName;
    }

    public Integer getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public String getMerchandisingAccountId()
    {
        return merchandisingAccountId;
    }

    public void setMerchandisingAccountId(String merchandisingAccountId)
    {
        this.merchandisingAccountId = merchandisingAccountId;
    }

    public String getMerchandisingAccountName()
    {
        return merchandisingAccountName;
    }

    public void setMerchandisingAccountName(String merchandisingAccountName)
    {
        this.merchandisingAccountName = merchandisingAccountName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getPackingInstructions()
    {
        return packingInstructions;
    }

    public void setPackingInstructions(String packingInstructions)
    {
        this.packingInstructions = packingInstructions;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public String getPersonalization()
    {
        return personalization;
    }

    public void setPersonalization(String personalization)
    {
        this.personalization = personalization;
    }

    public String getReceiptId()
    {
        return receiptId;
    }

    public void setReceiptId(String receiptId)
    {
        this.receiptId = receiptId;
    }

    public Integer getRejectedQuantity()
    {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(Integer rejectedQuantity)
    {
        this.rejectedQuantity = rejectedQuantity;
    }

    public String getRejectedReason()
    {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason)
    {
        this.rejectedReason = rejectedReason;
    }

    public List<String> getRetailerItemIds()
    {
        return retailerItemIds;
    }

    public void setRetailerItemIds(List<String> retailerItemIds)
    {
        this.retailerItemIds = retailerItemIds;
    }

    public Float getRetailerPrice()
    {
        return retailerPrice;
    }

    public void setRetailerPrice(Float retailerPrice)
    {
        this.retailerPrice = retailerPrice;
    }

    public String getShipInstructions()
    {
        return shipInstructions;
    }

    public void setShipInstructions(String shipInstructions)
    {
        this.shipInstructions = shipInstructions;
    }

    public Float getShippingSurcharge()
    {
        return shippingSurcharge;
    }

    public void setShippingSurcharge(Float shippingSurcharge)
    {
        this.shippingSurcharge = shippingSurcharge;
    }

    public Integer getSize()
    {
        return size;
    }

    public void setSize(Integer size)
    {
        this.size = size;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getStatusReason()
    {
        return statusReason;
    }

    public void setStatusReason(String statusReason)
    {
        this.statusReason = statusReason;
    }

    public List<OrderTax> getTaxes()
    {
        return taxes;
    }

    public void setTaxes(List<OrderTax> taxes)
    {
        this.taxes = taxes;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
