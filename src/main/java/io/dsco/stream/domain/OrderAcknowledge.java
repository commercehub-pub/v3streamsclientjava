package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class OrderAcknowledge
{
    public enum Type {DSCO_ORDER_ID, PO_NUMBER, SUPPLIER_ORDER_NUMBER}

    private String id;
    private Type type;
    private String supplierOrderNumber;

    public OrderAcknowledge(@NotNull String id, @NotNull Type type, String supplierOrderNumber)
    {
        this.id = id;
        this.type = type;
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getSupplierOrderNumber()
    {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber)
    {
        this.supplierOrderNumber = supplierOrderNumber;
    }
}
