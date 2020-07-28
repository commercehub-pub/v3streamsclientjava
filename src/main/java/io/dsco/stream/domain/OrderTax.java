package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class OrderTax {
	//MEMBERS
	/* The percentage of tax (this or typeCode is required) */
	private Float percentage;
	/* The type of tax (this or percentage is required) */
	private String typeCode;

	//CONSTRUCTORS
	public OrderTax() {}

	//ACCESSORS / MUTATORS
	public Float getPercentage() { return percentage; }
	public void setPercentage(Float val) { percentage = val; }
	public String getTypeCode() { return typeCode; }
	public void setTypeCode(String val) { typeCode = val; }
}