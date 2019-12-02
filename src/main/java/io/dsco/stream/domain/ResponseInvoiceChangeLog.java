package io.dsco.stream.domain;

import java.util.List;

public class ResponseInvoiceChangeLog
{
    private List<InvoiceChangeLog> logs;
    private String scrollId;

    public List<InvoiceChangeLog> getLogs()
    {
        return logs;
    }

    public void setLogs(List<InvoiceChangeLog> logs)
    {
        this.logs = logs;
    }

    public String getScrollId()
    {
        return scrollId;
    }

    public void setScrollId(String scrollId)
    {
        this.scrollId = scrollId;
    }
}
