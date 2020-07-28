package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class OrderPayment {
	//MEMBERS
	/* The type of card. */
	private String cardType;
	/* The payment card last four (this or cardType required) */
	private String cardLastFour;

	//CONSTRUCTORS
	public OrderPayment() {}

	//ACCESSORS / MUTATORS
	public String getCardType() { return cardType; }
	public void setCardType(String val) { cardType = val; }
	public String getCardLastFour() { return cardLastFour; }
	public void setCardLastFour(String val) { cardLastFour = val; }
}