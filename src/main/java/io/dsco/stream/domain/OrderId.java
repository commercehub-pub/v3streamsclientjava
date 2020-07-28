package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderId {
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
	/* The supplierOrderNumber to store on the order specified by the id. */
	private String supplierOrderNumber;

	//CONSTRUCTORS
	public OrderId() {}

	//ACCESSORS / MUTATORS
	public String getId() { return id; }
	public void setId(String val) { id = val; }
	public TYPE getType() { return type; }
	public void setType(TYPE val) { type = val; }
	public String getSupplierOrderNumber() { return supplierOrderNumber; }
	public void setSupplierOrderNumber(String val) { supplierOrderNumber = val; }
}