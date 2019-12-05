package io.dsco.stream.domain;

import java.util.List;

public class CreateOrderResponseMessageDescription
{
    private List<String> dscoOrderIds;
    //TODO: also a list of the orders


    public List<String> getDscoOrderIds()
    {
        return dscoOrderIds;
    }

    public void setDscoOrderIds(List<String> dscoOrderIds)
    {
        this.dscoOrderIds = dscoOrderIds;
    }
}
