package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class ItemWarehouse {
	//MEMBERS
	/* The supplier's unique ID for the warehouse */
	private String code;
	/* Dsco's unique ID for the warehouse */
	private String dscoId;
	/* The retailer's unique ID for the warehouse */
	private String retailerCode;
	/* The quantity of the associated item at this warehouse */
	private Integer quantity;
	/* The cost of the associated item at this warehouse */
	private Float cost;
	private String status;

	//CONSTRUCTORS
	public ItemWarehouse() {}

	//ACCESSORS / MUTATORS
	public String getCode() { return code; }
	public void setCode(String val) { code = val; }
	public String getDscoId() { return dscoId; }
	public void setDscoId(String val) { dscoId = val; }
	public String getRetailerCode() { return retailerCode; }
	public void setRetailerCode(String val) { retailerCode = val; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer val) { quantity = val; }
	public Float getCost() { return cost; }
	public void setCost(Float val) { cost = val; }
	public String getStatus() { return status; }
	public void setStatus(String val) { status = val; }
}