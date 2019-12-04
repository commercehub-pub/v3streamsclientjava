package io.dsco.stream.domain;

public class ShipmentLineItemForUpdate
{
    private int quantity;
    private String dscoItemId;
    private String ean;
    private Integer lineNumber;
    private String partnerSku;
    private String sku;
    private String upc;

    public ShipmentLineItemForUpdate(int quantity, String dscoItemId, String ean, Integer lineNumber, String partnerSku, String sku, String upc)
    {
        //one of the id fields is required
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the id fields is required");
        }

        this.quantity = quantity;
        this.dscoItemId = dscoItemId;
        this.ean = ean;
        this.lineNumber = lineNumber;
        this.partnerSku = partnerSku;
        this.sku = sku;
        this.upc = upc;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getDscoItemId()
    {
        return dscoItemId;
    }

    public void setDscoItemId(String dscoItemId)
    {
        this.dscoItemId = dscoItemId;
    }

    public String getEan()
    {
        return ean;
    }

    public void setEan(String ean)
    {
        this.ean = ean;
    }

    public Integer getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getUpc()
    {
        return upc;
    }

    public void setUpc(String upc)
    {
        this.upc = upc;
    }
}
