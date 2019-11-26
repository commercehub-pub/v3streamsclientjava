package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class OrderBillTo
extends Address
{
    public OrderBillTo(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region)
    {
        super(address1, city, firstName, lastName, postal, region, null, null, null, null, null, null);
    }

    public OrderBillTo(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region, String address2, String attention, String company,
            String country, String email, String phone)
    {
        super(address1, city, firstName, lastName, postal, region, address2, attention, company, country, email, phone);
    }
}
