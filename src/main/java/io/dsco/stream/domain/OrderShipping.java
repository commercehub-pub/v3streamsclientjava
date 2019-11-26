package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class OrderShipping
extends Address
{
    @Deprecated private String carrier;
    @Deprecated private String method;
    private String storeNumber;

    public OrderShipping(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region)
    {
        super(address1, city, firstName, lastName, postal, region, null, null, null, null, null, null);
    }

    public OrderShipping(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region, String address2, String attention, String company,
            String country, String email, String phone, String storeNumber)
    {
        super(address1, city, firstName, lastName, postal, region, address2, attention, company, country, email, phone);
        this.storeNumber = storeNumber;
    }

    public String getCarrier()
    {
        return carrier;
    }

    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getStoreNumber()
    {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber)
    {
        this.storeNumber = storeNumber;
    }
}
