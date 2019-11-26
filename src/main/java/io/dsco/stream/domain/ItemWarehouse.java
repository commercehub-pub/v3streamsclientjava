package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class ItemWarehouse
{
    private String code;
    private Float cost;
    private String dscoId;
    private Integer quantity;
    private String retailerCode;

    public ItemWarehouse() {}

    public ItemWarehouse(@NotNull String code, Float cost, String dscoId, Integer quantity, String retailerCode)
    {
        this.code = code;
        this.cost = cost;
        this.dscoId = dscoId;
        this.quantity = quantity;
        this.retailerCode = retailerCode;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Float getCost()
    {
        return cost;
    }

    public void setCost(Float cost)
    {
        this.cost = cost;
    }

    public String getDscoId()
    {
        return dscoId;
    }

    public void setDscoId(String dscoId)
    {
        this.dscoId = dscoId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getRetailerCode()
    {
        return retailerCode;
    }

    public void setRetailerCode(String retailerCode)
    {
        this.retailerCode = retailerCode;
    }
}
