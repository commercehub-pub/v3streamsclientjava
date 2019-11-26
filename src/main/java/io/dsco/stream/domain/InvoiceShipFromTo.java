package io.dsco.stream.domain;

public class InvoiceShipFromTo
extends Address
{
    private String locationCode;
    private String storeNumber;

    public String getLocationCode()
    {
        return locationCode;
    }

    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
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
