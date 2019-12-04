package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class OrderLineItemForCancel
{
    private String cancelCode;
    private int cancelledQuantity;
    private String cancelledReason;
    private String dscoItemId;
    private String dscoSupplierId;
    private String dscoTradingPartnerId;
    private String ean;
    private Integer lineNumber;
    private String partnerSku;
    private String sku;
    private String upc;

    public OrderLineItemForCancel(
            @NotNull String cancelCode, int cancelledQuantity, String cancelledReason, String dscoItemId,
            String dscoSupplierId, String dscoTradingPartnerId, String ean, Integer lineNumber, String partnerSku,
            String sku, String upc)
    {
        //one of the id fields must be present
        if (dscoItemId == null && dscoSupplierId == null && dscoTradingPartnerId == null && ean == null &&
            partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the id fields is required");
        }

        this.cancelCode = cancelCode;
        this.cancelledQuantity = cancelledQuantity;
        this.cancelledReason = cancelledReason;
        this.dscoItemId = dscoItemId;
        this.dscoSupplierId = dscoSupplierId;
        this.dscoTradingPartnerId = dscoTradingPartnerId;
        this.ean = ean;
        this.lineNumber = lineNumber;
        this.partnerSku = partnerSku;
        this.sku = sku;
        this.upc = upc;
    }

    public String getCancelCode()
    {
        return cancelCode;
    }

    public void setCancelCode(String cancelCode)
    {
        this.cancelCode = cancelCode;
    }

    public int getCancelledQuantity()
    {
        return cancelledQuantity;
    }

    public void setCancelledQuantity(int cancelledQuantity)
    {
        this.cancelledQuantity = cancelledQuantity;
    }

    public String getCancelledReason()
    {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason)
    {
        this.cancelledReason = cancelledReason;
    }

    public String getDscoItemId()
    {
        return dscoItemId;
    }

    public void setDscoItemId(String dscoItemId)
    {
        this.dscoItemId = dscoItemId;
    }

    public String getDscoSupplierId()
    {
        return dscoSupplierId;
    }

    public void setDscoSupplierId(String dscoSupplierId)
    {
        this.dscoSupplierId = dscoSupplierId;
    }

    public String getDscoTradingPartnerId()
    {
        return dscoTradingPartnerId;
    }

    public void setDscoTradingPartnerId(String dscoTradingPartnerId)
    {
        this.dscoTradingPartnerId = dscoTradingPartnerId;
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
