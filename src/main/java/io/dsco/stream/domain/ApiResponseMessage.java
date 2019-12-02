package io.dsco.stream.domain;

public class ApiResponseMessage
{
    public enum Severity {info, warn, error}

    private String code;
    private String description;
    private Severity severity;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Severity getSeverity()
    {
        return severity;
    }

    public void setSeverity(Severity severity)
    {
        this.severity = severity;
    }
}
