package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InvoiceChangeLog {
	//ENUMS
	public enum STATUS {
		@SerializedName("pending") PENDING,
		@SerializedName("success") SUCCESS,
		@SerializedName("failure") FAILURE
	}
	public enum REQUEST_METHOD {
		@SerializedName("api") API,
		@SerializedName("portal") PORTAL,
		@SerializedName("file") FILE
	}

	//MEMBERS
	/* The Invoice JSON object defines an invoice that may be created in the Dsco system.   */
	private Invoice payload;
	/* The date the request was actually processed */
	private Iso8601DateTime dateProcessed;
	/* The status of the change, one of...
* **pending**: the change is still being made
* **success**: the change was successfully made
* **failure**: the change failed, look at the results attribute */
	private STATUS status;
	/* how the request was made */
	private REQUEST_METHOD requestMethod;
	/* The ID of the API request that included the request to make this change if done via an API */
	private String requestId;
	/* The ID of the file process that  included the request to make this change if done via file process */
	private String processId;
	private List<ChangelogResponseMessage> results;
	/* **Note that this attribute has not yet been implemented but is planned to come online
by February 1, 2020.  It is being included now so developers can be aware it is coming and plan accordingly.**

This object shows what changed that caused the corresponding object to appear in a given change log.
Note that if an attempted change, say to update inventory, failed then this object will not be present
since no change was actually made. */
	private ChangeLogDiff diff;

	//CONSTRUCTORS
	public InvoiceChangeLog() {}

	//ACCESSORS / MUTATORS
	public Invoice getPayload() { return payload; }
	public void setPayload(Invoice val) { payload = val; }
	public Iso8601DateTime getDateProcessed() { return dateProcessed; }
	public void setDateProcessed(Iso8601DateTime val) { dateProcessed = val; }
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public REQUEST_METHOD getRequestMethod() { return requestMethod; }
	public void setRequestMethod(REQUEST_METHOD val) { requestMethod = val; }
	public String getRequestId() { return requestId; }
	public void setRequestId(String val) { requestId = val; }
	public String getProcessId() { return processId; }
	public void setProcessId(String val) { processId = val; }
	public List<ChangelogResponseMessage> getResults() { return results; }
	public void setResults(List<ChangelogResponseMessage> val) { results = val; }
	public ChangeLogDiff getDiff() { return diff; }
	public void setDiff(ChangeLogDiff val) { diff = val; }
}
