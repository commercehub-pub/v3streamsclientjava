package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StreamPartition {
	//ENUMS
	public enum STATUS {
		@SerializedName("active") ACTIVE,
		@SerializedName("inactive") INACTIVE
	}

	//MEMBERS
	/* The unique identifier for the partition */
	private int partitionId;
	/* Partitions may not be deleted, instead they may be inactivated which means they are not used; an inactivated
partition may be reactivated in which case it will be used once again */
	private STATUS status;
	/* A client-provided value identifying who owns the stream partition.  Dsco does not perform any validation on 
this value, merely taking whatever is provided by a client and storing it */
	private String ownerId;
	/* The position marks where the next object in this stream partitiono will be returned from when requested from this stream
partition.  This value may only be updated by a call to the Update Stream Position API.  Note that the id returned 
with each StreamEvent object can be used to set the position to that given object. */
	private String position;
	/* The position of the newest object in the stream; this is here for monitoring purposes, if the maxPosition is further
and further back in time then it's likely that your code to read from this stream partition is down and nothing
is reading from the stream; note that the maxPosition is an approximation that may be as old as one minute */
	private String maxPosition;
	/* Here are the scenarios that can cause the lastUpdate timestamp to change…
* Checkpoints: When a client updates the position of the StreamPartition
* Setting ownerId: Whenever the client sets the ownerId using the Create Stream Operation API, even if the client sends in the same ownerId and nothing was actually changed (this in effect allows the client to “touch” the StreamPartition to cause the lastUpdate attribute to update)
* On StreamPartition activate and deactivate: When a StreamPartition becomes active, either on initial creation or when a previously inactive stream becomes active as well as when a previously active streams is made inactive */
	private Iso8601DateTime lastUpdate;
	/* The date this stream partition was last inactivated if ever */
	private Iso8601DateTime lastDeactivatedDate;
	/* The date this stream partition was last activated; by default this will essentially be the create date of the stream partition */
	private Iso8601DateTime lastActivatedDate;

	//CONSTRUCTORS
	public StreamPartition() {}

	//ACCESSORS / MUTATORS
	public int getPartitionId() { return partitionId; }
	public void setPartitionId(int val) { partitionId = val; }
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public String getOwnerId() { return ownerId; }
	public void setOwnerId(String val) { ownerId = val; }
	public String getPosition() { return position; }
	public void setPosition(String val) { position = val; }
	public String getMaxPosition() { return maxPosition; }
	public void setMaxPosition(String val) { maxPosition = val; }
	public Iso8601DateTime getLastUpdate() { return lastUpdate; }
	public void setLastUpdate(Iso8601DateTime val) { lastUpdate = val; }
	public Iso8601DateTime getLastDeactivatedDate() { return lastDeactivatedDate; }
	public void setLastDeactivatedDate(Iso8601DateTime val) { lastDeactivatedDate = val; }
	public Iso8601DateTime getLastActivatedDate() { return lastActivatedDate; }
	public void setLastActivatedDate(Iso8601DateTime val) { lastActivatedDate = val; }
}