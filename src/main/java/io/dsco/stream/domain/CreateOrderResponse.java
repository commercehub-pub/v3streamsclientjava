package io.dsco.stream.domain;

import java.util.List;

public class CreateOrderResponse
{
    private String eventDate; //iso8601
    private String status;
    private List<String> dscoOrderIds;
    private List<Order> dscoOrders;

    public String getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<String> getDscoOrderIds()
    {
        return dscoOrderIds;
    }

    public void setDscoOrderIds(List<String> dscoOrderIds)
    {
        this.dscoOrderIds = dscoOrderIds;
    }

    public List<Order> getDscoOrders()
    {
        return dscoOrders;
    }

    public void setDscoOrders(List<Order> dscoOrders)
    {
        this.dscoOrders = dscoOrders;
    }
}
