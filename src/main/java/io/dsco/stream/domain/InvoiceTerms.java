package io.dsco.stream.domain;

public class InvoiceTerms
{
    private String basisDate; //iso8601
    private Integer dayOfMonth;
    private Float discountAmount;
    private Integer discountDaysDue;
    private String discountDueDate; //iso8601
    private Integer netDays;
    private String netDueDate; //iso8601
    private Float totalAmountSubjectToDiscount;
    private String type;

    public String getBasisDate()
    {
        return basisDate;
    }

    public void setBasisDate(String basisDate)
    {
        this.basisDate = basisDate;
    }

    public Integer getDayOfMonth()
    {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth)
    {
        this.dayOfMonth = dayOfMonth;
    }

    public Float getDiscountAmount()
    {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount)
    {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountDaysDue()
    {
        return discountDaysDue;
    }

    public void setDiscountDaysDue(Integer discountDaysDue)
    {
        this.discountDaysDue = discountDaysDue;
    }

    public String getDiscountDueDate()
    {
        return discountDueDate;
    }

    public void setDiscountDueDate(String discountDueDate)
    {
        this.discountDueDate = discountDueDate;
    }

    public Integer getNetDays()
    {
        return netDays;
    }

    public void setNetDays(Integer netDays)
    {
        this.netDays = netDays;
    }

    public String getNetDueDate()
    {
        return netDueDate;
    }

    public void setNetDueDate(String netDueDate)
    {
        this.netDueDate = netDueDate;
    }

    public Float getTotalAmountSubjectToDiscount()
    {
        return totalAmountSubjectToDiscount;
    }

    public void setTotalAmountSubjectToDiscount(Float totalAmountSubjectToDiscount)
    {
        this.totalAmountSubjectToDiscount = totalAmountSubjectToDiscount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
