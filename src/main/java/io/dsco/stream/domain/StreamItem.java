package io.dsco.stream.domain;

public abstract class StreamItem
{
    public enum Source { stream, sync }

    private String id; //the stream position
    private Source source;

    public StreamItem(String id, Source source)
    {
        this.id = id;
        this.source = source;
    }

    //a unique identifier for the object; what this is depends on the subtype
    abstract public String getKey();

    public String getId()
    {
        return id;
    }

    public Source getSource()
    {
        return source;
    }

}
