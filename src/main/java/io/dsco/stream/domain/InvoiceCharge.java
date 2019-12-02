package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class InvoiceCharge
{
    private float amount;
    private String title;

    public InvoiceCharge(float amount, @NotNull String title)
    {
        this.amount = amount;
        this.title = title;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
