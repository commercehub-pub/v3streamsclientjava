package io.dsco.stream.domain;

import java.util.List;

public class CreateOrderResponse
{
    private String eventDate; //iso8601
    private String requestId;
    private String status;
    private List<CreateOrderResponseMessage> messages;

    public String getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<CreateOrderResponseMessage> getMessages()
    {
        return messages;
    }

    public void setMessages(List<CreateOrderResponseMessage> messages)
    {
        this.messages = messages;
    }
}
