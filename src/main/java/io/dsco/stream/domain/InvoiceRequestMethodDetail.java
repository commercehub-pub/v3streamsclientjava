package io.dsco.stream.domain;

import java.util.List;

public class InvoiceRequestMethodDetail
{
    private String action; //TODO: should be an enum. what are the valid set of values?
    private long start_time_ms; //TODO: why isn't this an iso8601 date?
    private long end_time_ms; //TODO: why isn't this an iso8601 date?
    private List<InvoiceRequestMethodDetailMessage> messages;

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public long getStart_time_ms()
    {
        return start_time_ms;
    }

    public void setStart_time_ms(long start_time_ms)
    {
        this.start_time_ms = start_time_ms;
    }

    public long getEnd_time_ms()
    {
        return end_time_ms;
    }

    public void setEnd_time_ms(long end_time_ms)
    {
        this.end_time_ms = end_time_ms;
    }

    public List<InvoiceRequestMethodDetailMessage> getMessages()
    {
        return messages;
    }

    public void setMessages(List<InvoiceRequestMethodDetailMessage> messages)
    {
        this.messages = messages;
    }
}
