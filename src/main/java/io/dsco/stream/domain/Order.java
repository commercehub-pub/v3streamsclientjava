package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Order
{
    public enum OrderLifecycle {received, created, acknowledged, completed}

    private List<OrderLineItem> lineItems;
    private String poNumber;
    private OrderShipping shipping;
    private String acknowledgeByDate; //iso8601
    private OrderBillTo billTo;
    private String cancelAfterDate; //iso8601
    private String channel;
    private String consumerOrderNumber;
    private List<OrderCoupon> coupons;
    private String currencyCode;
    private String dscoAcknowledgeLateDate; //iso8601
    private String dscoCancelLateDate; //iso8601
    private String dscoCreateDate; //iso8601
    private String dscoInvoiceLateDate; //iso8601
    private String dscoLastUpdate; //iso8601 ?
    private OrderLifecycle dscoLifecycle;
    private String dscoOrderId;
    private String dscoRetailerId;
    private String dscoShipCarrier;
    private String dscoShipLateDate; //iso8601
    private String dscoShipMethod;
    private String dscoShippingServiceLevelCode;
    @Deprecated private String dscoStatus;
    private String dscoSupplierId;
    private String dscoTradingPartnerId;
    private String dscoTradingPartnerName;
    private String dscoWarehouseCode;
    private String dscoWarehouseDscoId;
    private String dscoWarehouseRetailerCode;
    private String expectedDeliveryDate; //iso8601
    private Boolean giftFlag;
    private String giftFromName;
    private String giftMessage;
    private String giftReceiptId;
    private String giftToName;
    private Boolean giftWrapFlag;
    private String giftWrapMessage;
    private String invoiceByDate; //iso8601
    private String marketingMessage;
    private String message;
    private Integer numberOfLineItems;
    private List<Package> packages;
    private String packingInstructions;
    private String packingSlipMessage;
    private List<OrderPayment> payments;
    private String receiptId;
    private String requestedWarehouseCode;
    private String requestedWarehouseDscoId;
    private String requestedWarehouseRetailerCode;
    private String requiredDeliveryDate; //iso8601
    private String retailerCreateDate; //iso8601
    private String retailerShipCarrier;
    private String retailerShipMethod;
    private String retailerShippingServiceLevelCode;
    private String shipByDate; //iso8601
    private String shipCarrier;
    private String shipInstructions;
    private String shipMethod;
    private String shippingServiceLevelCode;
    private Float shippingSurcharge;
    private String shipToStoreNumber;
    private String shipWarehouseCode;
    private String shipWarehouseDscoId;
    private String shipWarehouseRetailerCode;
    private Boolean signatureRequiredFlag;
    private String supplierOrderNumber;
    private List<OrderTax> taxes;
    private Boolean testFlag;

    public Order() {}

    public Order(
            @NotNull List<OrderLineItem> lineItems, @NotNull String poNumber, @NotNull OrderShipping shipping,
            @NotNull OrderBillTo billTo, @NotNull String expectedDeliveryDate, @NotNull String shippingServiceLevelCode)
    {
        this.lineItems = lineItems;
        this.poNumber = poNumber;
        this.shipping = shipping;
        this.billTo = billTo;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.shippingServiceLevelCode = shippingServiceLevelCode;
    }

    public Order(
            @NotNull List<OrderLineItem> lineItems, @NotNull String poNumber, @NotNull OrderShipping shipping, String acknowledgeByDate,
            @NotNull OrderBillTo billTo, String cancelAfterDate, String channel, String consumerOrderNumber, List<OrderCoupon> coupons,
            String currencyCode, String dscoAcknowledgeLateDate, String dscoCancelLateDate, String dscoCreateDate, String dscoInvoiceLateDate,
            String dscoLastUpdate, OrderLifecycle dscoLifecycle, String dscoRetailerId,
            String dscoShipCarrier, String dscoShipLateDate, String dscoShipMethod, String dscoShippingServiceLevelCode,
            String dscoStatus, String dscoSupplierId, String dscoTradingPartnerId, String dscoTradingPartnerName, String dscoWarehouseCode,
            String dscoWarehouseDscoId, String dscoWarehouseRetailerCode, String expectedDeliveryDate, Boolean giftFlag, String giftFromName,
            String giftMessage, String giftReceiptId, String giftToName, Boolean giftWrapFlag, String giftWrapMessage, String invoiceByDate,
            String marketingMessage, String message, Integer numberOfLineItems, List<Package> packages, String packingInstructions,
            String packingSlipMessage, List<OrderPayment> payments, String receiptId, String requestedWarehouseCode,
            String requestedWarehouseDscoId, String requestedWarehouseRetailerCode, String requiredDeliveryDate, String retailerCreateDate,
            String retailerShipCarrier, String retailerShipMethod, String retailerShippingServiceLevelCode, String shipByDate,
            String shipCarrier, String shipInstructions, String shipMethod, String shippingServiceLevelCode, Float shippingSurcharge,
            String shipToStoreNumber, String shipWarehouseCode, String shipWarehouseDscoId, String shipWarehouseRetailerCode,
            Boolean signatureRequiredFlag, String supplierOrderNumber, List<OrderTax> taxes, Boolean testFlag)
    {
        this.lineItems = lineItems;
        this.poNumber = poNumber;
        this.shipping = shipping;
        this.acknowledgeByDate = acknowledgeByDate;
        this.billTo = billTo;
        this.cancelAfterDate = cancelAfterDate;
        this.channel = channel;
        this.consumerOrderNumber = consumerOrderNumber;
        this.coupons = coupons;
        this.currencyCode = currencyCode;
        this.dscoAcknowledgeLateDate = dscoAcknowledgeLateDate;
        this.dscoCancelLateDate = dscoCancelLateDate;
        this.dscoCreateDate = dscoCreateDate;
        this.dscoInvoiceLateDate = dscoInvoiceLateDate;
        this.dscoLastUpdate = dscoLastUpdate;
        this.dscoLifecycle = dscoLifecycle;
        this.dscoRetailerId = dscoRetailerId;
        this.dscoShipCarrier = dscoShipCarrier;
        this.dscoShipLateDate = dscoShipLateDate;
        this.dscoShipMethod = dscoShipMethod;
        this.dscoShippingServiceLevelCode = dscoShippingServiceLevelCode;
        this.dscoStatus = dscoStatus;
        this.dscoSupplierId = dscoSupplierId;
        this.dscoTradingPartnerId = dscoTradingPartnerId;
        this.dscoTradingPartnerName = dscoTradingPartnerName;
        this.dscoWarehouseCode = dscoWarehouseCode;
        this.dscoWarehouseDscoId = dscoWarehouseDscoId;
        this.dscoWarehouseRetailerCode = dscoWarehouseRetailerCode;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.giftFlag = giftFlag;
        this.giftFromName = giftFromName;
        this.giftMessage = giftMessage;
        this.giftReceiptId = giftReceiptId;
        this.giftToName = giftToName;
        this.giftWrapFlag = giftWrapFlag;
        this.giftWrapMessage = giftWrapMessage;
        this.invoiceByDate = invoiceByDate;
        this.marketingMessage = marketingMessage;
        this.message = message;
        this.numberOfLineItems = numberOfLineItems;
        this.packages = packages;
        this.packingInstructions = packingInstructions;
        this.packingSlipMessage = packingSlipMessage;
        this.payments = payments;
        this.receiptId = receiptId;
        this.requestedWarehouseCode = requestedWarehouseCode;
        this.requestedWarehouseDscoId = requestedWarehouseDscoId;
        this.requestedWarehouseRetailerCode = requestedWarehouseRetailerCode;
        this.requiredDeliveryDate = requiredDeliveryDate;
        this.retailerCreateDate = retailerCreateDate;
        this.retailerShipCarrier = retailerShipCarrier;
        this.retailerShipMethod = retailerShipMethod;
        this.retailerShippingServiceLevelCode = retailerShippingServiceLevelCode;
        this.shipByDate = shipByDate;
        this.shipCarrier = shipCarrier;
        this.shipInstructions = shipInstructions;
        this.shipMethod = shipMethod;
        this.shippingServiceLevelCode = shippingServiceLevelCode;
        this.shippingSurcharge = shippingSurcharge;
        this.shipToStoreNumber = shipToStoreNumber;
        this.shipWarehouseCode = shipWarehouseCode;
        this.shipWarehouseDscoId = shipWarehouseDscoId;
        this.shipWarehouseRetailerCode = shipWarehouseRetailerCode;
        this.signatureRequiredFlag = signatureRequiredFlag;
        this.supplierOrderNumber = supplierOrderNumber;
        this.taxes = taxes;
        this.testFlag = testFlag;
    }

    public List<OrderLineItem> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<OrderLineItem> lineItems)
    {
        this.lineItems = lineItems;
    }

    public String getPoNumber()
    {
        return poNumber;
    }

    public void setPoNumber(String poNumber)
    {
        this.poNumber = poNumber;
    }

    public OrderShipping getShipping()
    {
        return shipping;
    }

    public void setShipping(OrderShipping shipping)
    {
        this.shipping = shipping;
    }

    public String getAcknowledgeByDate()
    {
        return acknowledgeByDate;
    }

    public void setAcknowledgeByDate(String acknowledgeByDate)
    {
        this.acknowledgeByDate = acknowledgeByDate;
    }

    public OrderBillTo getBillTo()
    {
        return billTo;
    }

    public void setBillTo(OrderBillTo billTo)
    {
        this.billTo = billTo;
    }

    public String getCancelAfterDate()
    {
        return cancelAfterDate;
    }

    public void setCancelAfterDate(String cancelAfterDate)
    {
        this.cancelAfterDate = cancelAfterDate;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getConsumerOrderNumber()
    {
        return consumerOrderNumber;
    }

    public void setConsumerOrderNumber(String consumerOrderNumber)
    {
        this.consumerOrderNumber = consumerOrderNumber;
    }

    public List<OrderCoupon> getCoupons()
    {
        return coupons;
    }

    public void setCoupons(List<OrderCoupon> coupons)
    {
        this.coupons = coupons;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getDscoAcknowledgeLateDate()
    {
        return dscoAcknowledgeLateDate;
    }

    public void setDscoAcknowledgeLateDate(String dscoAcknowledgeLateDate)
    {
        this.dscoAcknowledgeLateDate = dscoAcknowledgeLateDate;
    }

    public String getDscoCancelLateDate()
    {
        return dscoCancelLateDate;
    }

    public void setDscoCancelLateDate(String dscoCancelLateDate)
    {
        this.dscoCancelLateDate = dscoCancelLateDate;
    }

    public String getDscoCreateDate()
    {
        return dscoCreateDate;
    }

    public void setDscoCreateDate(String dscoCreateDate)
    {
        this.dscoCreateDate = dscoCreateDate;
    }

    public String getDscoInvoiceLateDate()
    {
        return dscoInvoiceLateDate;
    }

    public void setDscoInvoiceLateDate(String dscoInvoiceLateDate)
    {
        this.dscoInvoiceLateDate = dscoInvoiceLateDate;
    }

    public String getDscoLastUpdate()
    {
        return dscoLastUpdate;
    }

    public void setDscoLastUpdate(String dscoLastUpdate)
    {
        this.dscoLastUpdate = dscoLastUpdate;
    }

    public OrderLifecycle getDscoLifecycle()
    {
        return dscoLifecycle;
    }

    public void setDscoLifecycle(OrderLifecycle dscoLifecycle)
    {
        this.dscoLifecycle = dscoLifecycle;
    }

    public String getDscoOrderId()
    {
        return dscoOrderId;
    }

    public void setDscoOrderId(String dscoOrderId)
    {
        this.dscoOrderId = dscoOrderId;
    }

    public String getDscoRetailerId()
    {
        return dscoRetailerId;
    }

    public void setDscoRetailerId(String dscoRetailerId)
    {
        this.dscoRetailerId = dscoRetailerId;
    }

    public String getDscoShipCarrier()
    {
        return dscoShipCarrier;
    }

    public void setDscoShipCarrier(String dscoShipCarrier)
    {
        this.dscoShipCarrier = dscoShipCarrier;
    }

    public String getDscoShipLateDate()
    {
        return dscoShipLateDate;
    }

    public void setDscoShipLateDate(String dscoShipLateDate)
    {
        this.dscoShipLateDate = dscoShipLateDate;
    }

    public String getDscoShipMethod()
    {
        return dscoShipMethod;
    }

    public void setDscoShipMethod(String dscoShipMethod)
    {
        this.dscoShipMethod = dscoShipMethod;
    }

    public String getDscoShippingServiceLevelCode()
    {
        return dscoShippingServiceLevelCode;
    }

    public void setDscoShippingServiceLevelCode(String dscoShippingServiceLevelCode)
    {
        this.dscoShippingServiceLevelCode = dscoShippingServiceLevelCode;
    }

    public String getDscoStatus()
    {
        return dscoStatus;
    }

    public void setDscoStatus(String dscoStatus)
    {
        this.dscoStatus = dscoStatus;
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

    public String getDscoTradingPartnerName()
    {
        return dscoTradingPartnerName;
    }

    public void setDscoTradingPartnerName(String dscoTradingPartnerName)
    {
        this.dscoTradingPartnerName = dscoTradingPartnerName;
    }

    public String getDscoWarehouseCode()
    {
        return dscoWarehouseCode;
    }

    public void setDscoWarehouseCode(String dscoWarehouseCode)
    {
        this.dscoWarehouseCode = dscoWarehouseCode;
    }

    public String getDscoWarehouseDscoId()
    {
        return dscoWarehouseDscoId;
    }

    public void setDscoWarehouseDscoId(String dscoWarehouseDscoId)
    {
        this.dscoWarehouseDscoId = dscoWarehouseDscoId;
    }

    public String getDscoWarehouseRetailerCode()
    {
        return dscoWarehouseRetailerCode;
    }

    public void setDscoWarehouseRetailerCode(String dscoWarehouseRetailerCode)
    {
        this.dscoWarehouseRetailerCode = dscoWarehouseRetailerCode;
    }

    public String getExpectedDeliveryDate()
    {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate)
    {
        this.expectedDeliveryDate = expectedDeliveryDate;
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

    public Boolean getGiftWrapFlag()
    {
        return giftWrapFlag;
    }

    public void setGiftWrapFlag(Boolean giftWrapFlag)
    {
        this.giftWrapFlag = giftWrapFlag;
    }

    public String getGiftWrapMessage()
    {
        return giftWrapMessage;
    }

    public void setGiftWrapMessage(String giftWrapMessage)
    {
        this.giftWrapMessage = giftWrapMessage;
    }

    public String getInvoiceByDate()
    {
        return invoiceByDate;
    }

    public void setInvoiceByDate(String invoiceByDate)
    {
        this.invoiceByDate = invoiceByDate;
    }

    public String getMarketingMessage()
    {
        return marketingMessage;
    }

    public void setMarketingMessage(String marketingMessage)
    {
        this.marketingMessage = marketingMessage;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Integer getNumberOfLineItems()
    {
        return numberOfLineItems;
    }

    public void setNumberOfLineItems(Integer numberOfLineItems)
    {
        this.numberOfLineItems = numberOfLineItems;
    }

    public List<Package> getPackages()
    {
        return packages;
    }

    public void setPackages(List<Package> packages)
    {
        this.packages = packages;
    }

    public String getPackingInstructions()
    {
        return packingInstructions;
    }

    public void setPackingInstructions(String packingInstructions)
    {
        this.packingInstructions = packingInstructions;
    }

    public String getPackingSlipMessage()
    {
        return packingSlipMessage;
    }

    public void setPackingSlipMessage(String packingSlipMessage)
    {
        this.packingSlipMessage = packingSlipMessage;
    }

    public List<OrderPayment> getPayments()
    {
        return payments;
    }

    public void setPayments(List<OrderPayment> payments)
    {
        this.payments = payments;
    }

    public String getReceiptId()
    {
        return receiptId;
    }

    public void setReceiptId(String receiptId)
    {
        this.receiptId = receiptId;
    }

    public String getRequestedWarehouseCode()
    {
        return requestedWarehouseCode;
    }

    public void setRequestedWarehouseCode(String requestedWarehouseCode)
    {
        this.requestedWarehouseCode = requestedWarehouseCode;
    }

    public String getRequestedWarehouseDscoId()
    {
        return requestedWarehouseDscoId;
    }

    public void setRequestedWarehouseDscoId(String requestedWarehouseDscoId)
    {
        this.requestedWarehouseDscoId = requestedWarehouseDscoId;
    }

    public String getRequestedWarehouseRetailerCode()
    {
        return requestedWarehouseRetailerCode;
    }

    public void setRequestedWarehouseRetailerCode(String requestedWarehouseRetailerCode)
    {
        this.requestedWarehouseRetailerCode = requestedWarehouseRetailerCode;
    }

    public String getRequiredDeliveryDate()
    {
        return requiredDeliveryDate;
    }

    public void setRequiredDeliveryDate(String requiredDeliveryDate)
    {
        this.requiredDeliveryDate = requiredDeliveryDate;
    }

    public String getRetailerCreateDate()
    {
        return retailerCreateDate;
    }

    public void setRetailerCreateDate(String retailerCreateDate)
    {
        this.retailerCreateDate = retailerCreateDate;
    }

    public String getRetailerShipCarrier()
    {
        return retailerShipCarrier;
    }

    public void setRetailerShipCarrier(String retailerShipCarrier)
    {
        this.retailerShipCarrier = retailerShipCarrier;
    }

    public String getRetailerShipMethod()
    {
        return retailerShipMethod;
    }

    public void setRetailerShipMethod(String retailerShipMethod)
    {
        this.retailerShipMethod = retailerShipMethod;
    }

    public String getRetailerShippingServiceLevelCode()
    {
        return retailerShippingServiceLevelCode;
    }

    public void setRetailerShippingServiceLevelCode(String retailerShippingServiceLevelCode)
    {
        this.retailerShippingServiceLevelCode = retailerShippingServiceLevelCode;
    }

    public String getShipByDate()
    {
        return shipByDate;
    }

    public void setShipByDate(String shipByDate)
    {
        this.shipByDate = shipByDate;
    }

    public String getShipCarrier()
    {
        return shipCarrier;
    }

    public void setShipCarrier(String shipCarrier)
    {
        this.shipCarrier = shipCarrier;
    }

    public String getShipInstructions()
    {
        return shipInstructions;
    }

    public void setShipInstructions(String shipInstructions)
    {
        this.shipInstructions = shipInstructions;
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

    public Float getShippingSurcharge()
    {
        return shippingSurcharge;
    }

    public void setShippingSurcharge(Float shippingSurcharge)
    {
        this.shippingSurcharge = shippingSurcharge;
    }

    public String getShipToStoreNumber()
    {
        return shipToStoreNumber;
    }

    public void setShipToStoreNumber(String shipToStoreNumber)
    {
        this.shipToStoreNumber = shipToStoreNumber;
    }

    public String getShipWarehouseCode()
    {
        return shipWarehouseCode;
    }

    public void setShipWarehouseCode(String shipWarehouseCode)
    {
        this.shipWarehouseCode = shipWarehouseCode;
    }

    public String getShipWarehouseDscoId()
    {
        return shipWarehouseDscoId;
    }

    public void setShipWarehouseDscoId(String shipWarehouseDscoId)
    {
        this.shipWarehouseDscoId = shipWarehouseDscoId;
    }

    public String getShipWarehouseRetailerCode()
    {
        return shipWarehouseRetailerCode;
    }

    public void setShipWarehouseRetailerCode(String shipWarehouseRetailerCode)
    {
        this.shipWarehouseRetailerCode = shipWarehouseRetailerCode;
    }

    public Boolean getSignatureRequiredFlag()
    {
        return signatureRequiredFlag;
    }

    public void setSignatureRequiredFlag(Boolean signatureRequiredFlag)
    {
        this.signatureRequiredFlag = signatureRequiredFlag;
    }

    public String getSupplierOrderNumber()
    {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber)
    {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public List<OrderTax> getTaxes()
    {
        return taxes;
    }

    public void setTaxes(List<OrderTax> taxes)
    {
        this.taxes = taxes;
    }

    public Boolean getTestFlag()
    {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag)
    {
        this.testFlag = testFlag;
    }
}
