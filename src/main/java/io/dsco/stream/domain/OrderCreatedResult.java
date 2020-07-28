package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderCreatedResult {
	//ENUMS
	public enum STATUS {
		@SerializedName("success") SUCCESS,
		@SerializedName("failure") FAILURE
	}

	//MEMBERS
	/* A list containing Dsco's unique ID for each order that was created - remember that one retailer
purchase order may result in multiple consumer orders being created and sent to suppliers */
	private List<String> dscoOrderIds;
	/* A list containing Dsco's orders that were created - remember that one retailer
purchase order may result in multiple consumer orders being created and sent to suppliers */
	private List<Order> dscoOrders;
	/* The status of the operation */
	private STATUS status;
	private Iso8601DateTime eventDate;

	//CONSTRUCTORS
	public OrderCreatedResult() {}

	//ACCESSORS / MUTATORS
	public List<String> getDscoOrderIds() { return dscoOrderIds; }
	public void setDscoOrderIds(List<String> val) { dscoOrderIds = val; }
	public List<Order> getDscoOrders() { return dscoOrders; }
	public void setDscoOrders(List<Order> val) { dscoOrders = val; }
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public Iso8601DateTime getEventDate() { return eventDate; }
	public void setEventDate(Iso8601DateTime val) { eventDate = val; }
}