package io.dsco.stream.domain;

public class InvoiceRequestMethodDetailMessage
{
    private String type; //TODO: should be an enum; what are the valid set of values?
    private String message;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
