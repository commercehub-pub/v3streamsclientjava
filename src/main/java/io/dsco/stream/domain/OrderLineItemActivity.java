package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderLineItemActivity {
	//ENUMS
	public enum ACTION {
		@SerializedName("accept") ACCEPT,
		@SerializedName("add") ADD,
		@SerializedName("cancel") CANCEL,
		@SerializedName("invoice") INVOICE,
		@SerializedName("reason_update") REASON_UPDATE,
		@SerializedName("reject") REJECT,
		@SerializedName("remove_status") REMOVE_STATUS,
		@SerializedName("ship") SHIP
	}
	public enum FORMER_STATUS {
		@SerializedName("accept") ACCEPT,
		@SerializedName("add") ADD,
		@SerializedName("cancel") CANCEL,
		@SerializedName("invoice") INVOICE,
		@SerializedName("reason_update") REASON_UPDATE,
		@SerializedName("reject") REJECT,
		@SerializedName("remove_status") REMOVE_STATUS,
		@SerializedName("ship") SHIP
	}

	//MEMBERS
	/* The type of activity that occurred */
	private ACTION action;
	/* Quantity involved in change if any */
	private Integer quantity;
	/* The previous status before this change */
	private FORMER_STATUS formerStatus;
	/* The reason for the change */
	private String reason;
	/* The date of the change */
	private Iso8601DateTime activityDate;

	//CONSTRUCTORS
	public OrderLineItemActivity() {}

	//ACCESSORS / MUTATORS
	public ACTION getAction() { return action; }
	public void setAction(ACTION val) { action = val; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer val) { quantity = val; }
	public FORMER_STATUS getFormerStatus() { return formerStatus; }
	public void setFormerStatus(FORMER_STATUS val) { formerStatus = val; }
	public String getReason() { return reason; }
	public void setReason(String val) { reason = val; }
	public Iso8601DateTime getActivityDate() { return activityDate; }
	public void setActivityDate(Iso8601DateTime val) { activityDate = val; }
}