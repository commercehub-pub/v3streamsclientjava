package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class PackageShipFrom
extends Address
{
    private String locationCode;

    public PackageShipFrom(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region, String address2, String attention, String company,
            String country, String email, String phone, String locationCode)
    {
        super(address1, city, firstName, lastName, postal, region, address2, attention, company, country, email, phone);
        this.locationCode = locationCode;
    }

    public String getLocationCode()
    {
        return locationCode;
    }

    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
}
