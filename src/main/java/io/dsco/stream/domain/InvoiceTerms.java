package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class InvoiceTerms {
	//MEMBERS
	private String type;
	private Iso8601DateTime basisDate;
	private Float discountPercent;
	private Iso8601DateTime discountDueDate;
	private Integer discountDaysDue;
	private Iso8601DateTime netDueDate;
	private Integer netDays;
	private Float discountAmount;
	private Integer dayOfMonth;
	private Float totalAmountSubjectToDiscount;

	//CONSTRUCTORS
	public InvoiceTerms() {}

	//ACCESSORS / MUTATORS
	public String getType() { return type; }
	public void setType(String val) { type = val; }
	public Iso8601DateTime getBasisDate() { return basisDate; }
	public void setBasisDate(Iso8601DateTime val) { basisDate = val; }
	public Float getDiscountPercent() { return discountPercent; }
	public void setDiscountPercent(Float val) { discountPercent = val; }
	public Iso8601DateTime getDiscountDueDate() { return discountDueDate; }
	public void setDiscountDueDate(Iso8601DateTime val) { discountDueDate = val; }
	public Integer getDiscountDaysDue() { return discountDaysDue; }
	public void setDiscountDaysDue(Integer val) { discountDaysDue = val; }
	public Iso8601DateTime getNetDueDate() { return netDueDate; }
	public void setNetDueDate(Iso8601DateTime val) { netDueDate = val; }
	public Integer getNetDays() { return netDays; }
	public void setNetDays(Integer val) { netDays = val; }
	public Float getDiscountAmount() { return discountAmount; }
	public void setDiscountAmount(Float val) { discountAmount = val; }
	public Integer getDayOfMonth() { return dayOfMonth; }
	public void setDayOfMonth(Integer val) { dayOfMonth = val; }
	public Float getTotalAmountSubjectToDiscount() { return totalAmountSubjectToDiscount; }
	public void setTotalAmountSubjectToDiscount(Float val) { totalAmountSubjectToDiscount = val; }
}