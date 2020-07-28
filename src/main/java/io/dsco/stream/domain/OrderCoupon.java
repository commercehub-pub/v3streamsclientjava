package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class OrderCoupon {
	//MEMBERS
	/* The amount of the coupon (this or percentage required) */
	private Float amount;
	/* The percentage of the coupon (this or amount required) */
	private Float percentage;

	//CONSTRUCTORS
	public OrderCoupon() {}

	//ACCESSORS / MUTATORS
	public Float getAmount() { return amount; }
	public void setAmount(Float val) { amount = val; }
	public Float getPercentage() { return percentage; }
	public void setPercentage(Float val) { percentage = val; }
}