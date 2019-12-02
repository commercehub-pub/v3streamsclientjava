package io.dsco.stream.domain;

import java.util.List;

//map the response of /invoice/batch/small to a java object
public class ResponseInvoiceCreate
{
    public enum Status {success, failure}

    private String eventDate; //iso8601
    private String requestId;
    private Status status;
    private List<ApiResponseMessage> messages;

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

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public List<ApiResponseMessage> getMessages()
    {
        return messages;
    }

    public void setMessages(List<ApiResponseMessage> messages)
    {
        this.messages = messages;
    }
}
