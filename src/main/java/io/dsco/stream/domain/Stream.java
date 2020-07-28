package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Stream {
	//ENUMS
	public enum OBJECT_TYPE {
		@SerializedName("order") ORDER,
		@SerializedName("invoice") INVOICE,
		@SerializedName("inventory") INVENTORY,
		@SerializedName("catalog") CATALOG,
		@SerializedName("catalogchangelog") CATALOGCHANGELOG,
		@SerializedName("orderitemchange") ORDERITEMCHANGE,
		@SerializedName("undeliverableshipment") UNDELIVERABLESHIPMENT
	}

	//MEMBERS
	/* A unique ID for this steam.  Use this ID in APIs to get events from the stream. */
	private String id;
	private String description;
	/* The type of object that will flow through the stream. */
	private OBJECT_TYPE objectType;
	/* A list of stream partitions; once a partition is created it can never be deleted but instead will 
continue to exist in this list but its status will be "inactive" */
	private List<StreamPartition> partitions;
	/* The number of partitions in the stream.  When updating the stream object, if this number is greater
than the number of stream partitions that already exist then new partitions will be created and
events will begin flowing into them.  When updating the stream object, if this number is less than the number of existing
stream partitions then the oldest partitions will be inactived to get to the new number.  So, if there were
four partitions (1 2 3 4) and this number is set to two then partitions 3 and 4 will be inactivated. */
	private int numPartitions;
	/* This object allows the developer to override default max values for the number of events that will be returned for each call to get events from any stream partitions of this stream.  Note that if you specify more than one max setting then the first setting that is hit will be used. */
	private StreamMaxEvents maxEvents;

	//CONSTRUCTORS
	public Stream() {}

	//ACCESSORS / MUTATORS
	public String getId() { return id; }
	public void setId(String val) { id = val; }
	public String getDescription() { return description; }
	public void setDescription(String val) { description = val; }
	public OBJECT_TYPE getObjectType() { return objectType; }
	public void setObjectType(OBJECT_TYPE val) { objectType = val; }
	public List<StreamPartition> getPartitions() { return partitions; }
	public void setPartitions(List<StreamPartition> val) { partitions = val; }
	public int getNumPartitions() { return numPartitions; }
	public void setNumPartitions(int val) { numPartitions = val; }
	public StreamMaxEvents getMaxEvents() { return maxEvents; }
	public void setMaxEvents(StreamMaxEvents val) { maxEvents = val; }
}