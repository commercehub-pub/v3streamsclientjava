package io.dsco.stream.domain;

import java.util.List;

public class StreamEventsResult<T>
{
    private String ownerId;
    private int partitionId;
    private List<StreamEvent<T>> events;

    public StreamEventsResult(String ownerId, int partitionId, List<StreamEvent<T>> events)
    {
        this.ownerId = ownerId;
        this.partitionId = partitionId;
        this.events = events;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public int getPartitionId()
    {
        return partitionId;
    }

    public void setPartitionId(int partitionId)
    {
        this.partitionId = partitionId;
    }

    public List<StreamEvent<T>> getEvents()
    {
        return events;
    }

    public void setEvents(List<StreamEvent<T>> events)
    {
        this.events = events;
    }
}
