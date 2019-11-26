package io.dsco.stream.domain;

import org.jetbrains.annotations.Nullable;

public class OrderTax
{
    private Float percentage;
    private String typeCode;

    public OrderTax() {}

    public OrderTax(@Nullable Float percentage, @Nullable String typeCode)
    {
        if (percentage == null && typeCode == null) {
            throw new IllegalArgumentException("typeCode or percentage is required");
        }

        this.percentage = percentage;
        this.typeCode = typeCode;
    }

    public Float getPercentage()
    {
        return percentage;
    }

    public void setPercentage(Float percentage)
    {
        this.percentage = percentage;
    }

    public String getTypeCode()
    {
        return typeCode;
    }

    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }
}
