package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SyncUpdateResponse {
	//ENUMS
	public enum STATUS {
		@SerializedName("success") SUCCESS,
		@SerializedName("failure") FAILURE,
		@SerializedName("pending") PENDING
	}

	//MEMBERS
	/* Whether it worked or not. */
	private STATUS status;
	/* A unique ID representing this one request */
	private String requestId;
	/* The timestamp of the request */
	private Iso8601DateTime eventDate;
	private List<ApiResponseMessage> messages;

	//CONSTRUCTORS
	public SyncUpdateResponse() {}

	//ACCESSORS / MUTATORS
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public String getRequestId() { return requestId; }
	public void setRequestId(String val) { requestId = val; }
	public Iso8601DateTime getEventDate() { return eventDate; }
	public void setEventDate(Iso8601DateTime val) { eventDate = val; }
	public List<ApiResponseMessage> getMessages() { return messages; }
	public void setMessages(List<ApiResponseMessage> val) { messages = val; }
}