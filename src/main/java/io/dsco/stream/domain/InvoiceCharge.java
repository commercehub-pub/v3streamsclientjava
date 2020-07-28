package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class InvoiceCharge {
	//MEMBERS
	private String title;
	private float amount;

	//CONSTRUCTORS
	public InvoiceCharge() {}

	//ACCESSORS / MUTATORS
	public String getTitle() { return title; }
	public void setTitle(String val) { title = val; }
	public float getAmount() { return amount; }
	public void setAmount(float val) { amount = val; }
}