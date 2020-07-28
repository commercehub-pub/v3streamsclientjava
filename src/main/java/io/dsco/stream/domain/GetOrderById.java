package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetOrderById {
	//ENUMS
	public enum ORDER_KEY {
		@SerializedName("dscoOrderId") DSCO_ORDER_ID,
		@SerializedName("poNumber") PO_NUMBER,
		@SerializedName("supplierOrderNumber") SUPPLIER_ORDER_NUMBER
	}

	//MEMBERS
	/* Indicates which order identifier to query for; if supplierOrderNumber
is specified and the account making the API call is a retailer then either
the dscoAccountId or dscoTradingPartnerId param is required and should be
the supplier's account */
	private ORDER_KEY orderKey;
	/* The value of the order identifier to find */
	private String value;
	/* Dsco's unique ID for the trading partner of the caller of this API; this or dscoTradingPartnerId is
required if orderKey is supplierOrderNumber and the caller is a retailer */
	private String dscoAccountId;
	/* The caller's unique ID for the trading partner in question; this or dscoAccountId
is required if orderKey is supplierOrderNumber and the caller is a retailer */
	private String dscoTradingPartnerId;

	//CONSTRUCTORS
	public GetOrderById() {}

	//ACCESSORS / MUTATORS
	public ORDER_KEY getOrderKey() { return orderKey; }
	public void setOrderKey(ORDER_KEY val) { orderKey = val; }
	public String getValue() { return value; }
	public void setValue(String val) { value = val; }
	public String getDscoAccountId() { return dscoAccountId; }
	public void setDscoAccountId(String val) { dscoAccountId = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
}