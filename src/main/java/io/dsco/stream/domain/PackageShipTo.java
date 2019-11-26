package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class PackageShipTo
extends Address
{
    private String storeNumber;

    public PackageShipTo(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region, String address2, String attention, String company,
            String country, String email, String phone, String storeNumber)
    {
        super(address1, city, firstName, lastName, postal, region, address2, attention, company, country, email, phone);
        this.storeNumber = storeNumber;
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
