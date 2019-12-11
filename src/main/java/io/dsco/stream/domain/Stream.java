package io.dsco.stream.domain;

import io.dsco.stream.api.StreamV3Api;

import java.util.List;

public class Stream
{
    private String id;
    private StreamV3Api.ObjectType objectType;
    private List<StreamPartition> partitions;
    //TODO: add in the query type. the type of query will depend on the type of stream

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public StreamV3Api.ObjectType getObjectType()
    {
        return objectType;
    }

    public void setObjectType(StreamV3Api.ObjectType objectType)
    {
        this.objectType = objectType;
    }

    public List<StreamPartition> getPartitions()
    {
        return partitions;
    }

    public void setPartitions(List<StreamPartition> partitions)
    {
        this.partitions = partitions;
    }

    public int getNumPartitions()
    {
        return partitions == null ? 0 : partitions.size();
    }
}
