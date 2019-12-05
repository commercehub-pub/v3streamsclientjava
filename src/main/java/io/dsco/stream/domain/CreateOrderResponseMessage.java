package io.dsco.stream.domain;

public class CreateOrderResponseMessage
{
    private String code;
    private String severity;
    private CreateOrderResponseMessageDescription description;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getSeverity()
    {
        return severity;
    }

    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    public CreateOrderResponseMessageDescription getDescription()
    {
        return description;
    }

    public void setDescription(CreateOrderResponseMessageDescription description)
    {
        this.description = description;
    }
}
