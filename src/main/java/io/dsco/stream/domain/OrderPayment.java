package io.dsco.stream.domain;

public class OrderPayment
{
    private String cardLastFour;
    private String cardType;

    public OrderPayment() {}

    public OrderPayment(String cardLastFour, String cardType)
    {
        if (cardLastFour == null && cardType == null) {
            throw new IllegalArgumentException("one of cardLastFour or cardType is required");
        }

        this.cardLastFour = cardLastFour;
        this.cardType = cardType;
    }

    public String getCardLastFour()
    {
        return cardLastFour;
    }

    public void setCardLastFour(String cardLastFour)
    {
        this.cardLastFour = cardLastFour;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }
}
