package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderCancelItem
{
    public enum Type { DSCO_ORDER_ID, PO_NUMBER, SUPPLIER_ORDER_NUMBER }

    private String id;
    private List<OrderLineItemForCancel> lineItems;
    private Type type;

    public OrderCancelItem(@NotNull String id, @NotNull List<OrderLineItemForCancel> lineItems, @NotNull Type type)
    {
        this.id = id;
        this.lineItems = lineItems;
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<OrderLineItemForCancel> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<OrderLineItemForCancel> lineItems)
    {
        this.lineItems = lineItems;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }
}
