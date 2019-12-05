package io.dsco.stream.domain;

public class GetOrderRequest
{
    public enum OrderKey { dscoOrderId, poNumber, supplierOrderNumber }

    private OrderKey orderKey;
    private String value;
    private String dscoAccountId;
    private String dscoTradingPartnerId;

    public GetOrderRequest(OrderKey orderKey, String value, String dscoAccountId, String dscoTradingPartnerId)
    {
        this.orderKey = orderKey;
        this.value = value;
        this.dscoAccountId = dscoAccountId;
        this.dscoTradingPartnerId = dscoTradingPartnerId;
    }

    public OrderKey getOrderKey()
    {
        return orderKey;
    }

    public void setOrderKey(OrderKey orderKey)
    {
        this.orderKey = orderKey;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getDscoAccountId()
    {
        return dscoAccountId;
    }

    public void setDscoAccountId(String dscoAccountId)
    {
        this.dscoAccountId = dscoAccountId;
    }

    public String getDscoTradingPartnerId()
    {
        return dscoTradingPartnerId;
    }

    public void setDscoTradingPartnerId(String dscoTradingPartnerId)
    {
        this.dscoTradingPartnerId = dscoTradingPartnerId;
    }
}
