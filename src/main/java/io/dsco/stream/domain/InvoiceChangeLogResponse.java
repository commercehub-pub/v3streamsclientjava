package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class InvoiceChangeLogResponse {
	//MEMBERS
	/* If the request results were too large to return in a single request, this attribute
will contain a value.  To get the next page of results, simply provide this 
value in the search query.  The pages of results are cached and expire if the next
page of results isn't retrieved within the default expire period for searches.  
Unless otherwise specified by the search API itself, the default expiry is
five minutes.  This value will not change between requests to get pages.  If
the page doesn't exist, perhaps it expired, the API will return an HTTP 404 error. */
	private String scrollId;
	/* The invoice change logs matching the request */
	private List<InvoiceChangeLog> logs;

	//CONSTRUCTORS
	public InvoiceChangeLogResponse() {}

	//ACCESSORS / MUTATORS
	public String getScrollId() { return scrollId; }
	public void setScrollId(String val) { scrollId = val; }
	public List<InvoiceChangeLog> getLogs() { return logs; }
	public void setLogs(List<InvoiceChangeLog> val) { logs = val; }
}