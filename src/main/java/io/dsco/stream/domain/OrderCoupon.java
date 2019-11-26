package io.dsco.stream.domain;

public class OrderCoupon
{
    private Float amount;
    private Float percentage;

    public OrderCoupon() {}

    public OrderCoupon(Float amount, Float percentage)
    {
        if (amount == null && percentage == null) {
            throw new IllegalArgumentException("amount or percentage is required");
        }

        this.amount = amount;
        this.percentage = percentage;
    }

    public Float getAmount()
    {
        return amount;
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }

    public Float getPercentage()
    {
        return percentage;
    }

    public void setPercentage(Float percentage)
    {
        this.percentage = percentage;
    }
}
