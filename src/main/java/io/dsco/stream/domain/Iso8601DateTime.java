package io.dsco.stream.domain;

public class Iso8601DateTime extends Object 
{
    private String date;

    public Iso8601DateTime() {}
    
    public Iso8601DateTime(String date) { this.date = date; }
    
    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }
}