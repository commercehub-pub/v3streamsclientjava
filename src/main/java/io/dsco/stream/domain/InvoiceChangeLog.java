package io.dsco.stream.domain;

import com.google.gson.Gson;

import java.util.List;

public class InvoiceChangeLog
{
    public enum RequestMethod {api, portal, file}
    public enum Status {pending, success, failure}

    private String dateProcessed; //iso8601
    private InvoiceForUpdate payload;
    private RequestMethod requestMethod;
    private Status status;
    //diff: coming soon...
    private String processId;
    private String requestId;
    private InvoiceRequestMethodDetail requestMethodDetail;
    private List<ApiResponseMessage> results;

    public String getDateProcessed()
    {
        return dateProcessed;
    }

    public void setDateProcessed(String dateProcessed)
    {
        this.dateProcessed = dateProcessed;
    }

    public InvoiceForUpdate getPayload()
    {
        return payload;
    }

    public void setPayload(InvoiceForUpdate payload)
    {
        this.payload = payload;
    }

    public RequestMethod getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getProcessId()
    {
        return processId;
    }

    public void setProcessId(String processId)
    {
        this.processId = processId;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public InvoiceRequestMethodDetail getRequestMethodDetail()
    {
        return requestMethodDetail;
    }

    public void setRequestMethodDetail(InvoiceRequestMethodDetail requestMethodDetail)
    {
        this.requestMethodDetail = requestMethodDetail;
    }

    public List<ApiResponseMessage> getResults()
    {
        return results;
    }

    public void setResults(List<ApiResponseMessage> results)
    {
        this.results = results;
    }

    public String getResultsAsJson()
    {
        return new Gson().toJson(results);
    }
}
