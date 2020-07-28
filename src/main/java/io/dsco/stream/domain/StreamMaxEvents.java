package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class StreamMaxEvents {
	//MEMBERS
	/* The maximum number of events to return for each call to get events from any partitions of this stream.
The actual number of objects returned may be less as there is a limit on the number of bytes that may
be returned to clients at a time. */
	private int objectCount;
	/* The maximum number of bytes to return for each call to get events from any partitions of this stream.
The actual number of bytes returned may be less as there is a limit on the number of bytes that may
be returned to clients at a time. */
	private int numBytes;
	/* The maximum number of seconds worth of events to return for each call to get events from any partitions of this stream.
The actual number of events returned may be less as there is a limit on the number of bytes that may
be returned to clients at a time. */
	private int durationSec;

	//CONSTRUCTORS
	public StreamMaxEvents() {}

	//ACCESSORS / MUTATORS
	public int getObjectCount() { return objectCount; }
	public void setObjectCount(int val) { objectCount = val; }
	public int getNumBytes() { return numBytes; }
	public void setNumBytes(int val) { numBytes = val; }
	public int getDurationSec() { return durationSec; }
	public void setDurationSec(int val) { durationSec = val; }
}