package io.dsco.stream.domain;

public class StreamPartition
{
    public enum Status { active, inactive }

    private int partitionId;
    private Status status;
    private String position;
    private String maxPosition;
    private String positionLastUpdated; //iso8601
    private String lastDeactivatedDate; //iso8601
    private String lastActivatedDate; //iso8601

    public int getPartitionId()
    {
        return partitionId;
    }

    public void setPartitionId(int partitionId)
    {
        this.partitionId = partitionId;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getMaxPosition()
    {
        return maxPosition;
    }

    public void setMaxPosition(String maxPosition)
    {
        this.maxPosition = maxPosition;
    }

    public String getPositionLastUpdated()
    {
        return positionLastUpdated;
    }

    public void setPositionLastUpdated(String positionLastUpdated)
    {
        this.positionLastUpdated = positionLastUpdated;
    }

    public String getLastDeactivatedDate()
    {
        return lastDeactivatedDate;
    }

    public void setLastDeactivatedDate(String lastDeactivatedDate)
    {
        this.lastDeactivatedDate = lastDeactivatedDate;
    }

    public String getLastActivatedDate()
    {
        return lastActivatedDate;
    }

    public void setLastActivatedDate(String lastActivatedDate)
    {
        this.lastActivatedDate = lastActivatedDate;
    }
}
