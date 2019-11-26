package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public class PartnerSkuMap
{
    private String partnerSku;
    private String dscoRetailerId;
    private String dscoTradingPartnerId;

    public PartnerSkuMap(@NotNull String partnerSku, String dscoRetailerId, String dscoTradingPartnerId)
    {
        this.partnerSku = partnerSku;
        this.dscoRetailerId = dscoRetailerId;
        this.dscoTradingPartnerId = dscoTradingPartnerId;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public String getDscoRetailerId()
    {
        return dscoRetailerId;
    }

    public void setDscoRetailerId(String dscoRetailerId)
    {
        this.dscoRetailerId = dscoRetailerId;
    }

    public String getDscoTradingPartnerId()
    {
        return dscoTradingPartnerId;
    }

    public void setDscoTradingPartnerId(String dscoTradingPartnerId)
    {
        this.dscoTradingPartnerId = dscoTradingPartnerId;
    }
}
