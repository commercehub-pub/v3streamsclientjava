package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderForCancel {
	//ENUMS
	public enum TYPE {
		@SerializedName("DSCO_ORDER_ID") DSCO_ORDER_ID,
		@SerializedName("PO_NUMBER") PO_NUMBER,
		@SerializedName("SUPPLIER_ORDER_NUMBER") SUPPLIER_ORDER_NUMBER
	}

	//MEMBERS
	/* The unique ID of the order */
	private String id;
	/* The type of order ID provided, one of these...
* **DSCO_ORDER_ID**: indicates the id is a dscoOrderId
* **PO_NUMBER**: indicates the id is a poNumber
* **SUPPLIER_ORDER_NUMBER**: indicates the id is a supplierOrderNumber */
	private TYPE type;
	/* The line items to be cancelled. */
	private List<OrderLineItemForCancel> lineItems;

	//CONSTRUCTORS
	public OrderForCancel() {}

	//ACCESSORS / MUTATORS
	public String getId() { return id; }
	public void setId(String val) { id = val; }
	public TYPE getType() { return type; }
	public void setType(TYPE val) { type = val; }
	public List<OrderLineItemForCancel> getLineItems() { return lineItems; }
	public void setLineItems(List<OrderLineItemForCancel> val) { lineItems = val; }
}