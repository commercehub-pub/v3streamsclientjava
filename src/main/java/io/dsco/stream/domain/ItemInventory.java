package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemInventory
{
    private int quantityAvailable;
    private String sku;
    private List<ItemWarehouse> warehouses;
    private Float cost;
    private String currencyCode;
    private String dscoCreateDate; //iso8601
    private String dscoItemId;
    private String dscoLatCostUpdateDate; //iso8601
    private String dscoLastQuantityUpdateDate; //iso8601
    private String dscoLastUpdateDate; //iso8601
    private String dscoSupplierId;
    private String dscoSupplierName;
    private String ean;
    private String estimatedAvailabilityDate; //iso8601
    private String gtim;
    private String isbn;
    private String mpn;
    private String partnerSku;
    private List<PartnerSkuMap> partnerSkuMap;
    private Integer quanityOnOrder;
    private String status; //this should be an enum, but java enums can't contain hyphens; will validate via code
    private String tradingPartnerId;
    private String tradingPartnerName;
    private String upc;

    public ItemInventory() {}

    public ItemInventory(
            int quantityAvailable, @NotNull String sku, @NotNull List<ItemWarehouse> warehouses, Float cost,
            String currencyCode, String dscoCreateDate, String dscoItemId, String dscoLatCostUpdateDate,
            String dscoLastQuantityUpdateDate, String dscoLastUpdateDate, String dscoSupplierId,
            String dscoSupplierName, String ean, String estimatedAvailabilityDate, String gtim, String isbn,
            String mpn, String partnerSku, List<PartnerSkuMap> partnerSkuMap, Integer quanityOnOrder, String status,
            String tradingPartnerId, String tradingPartnerName, String upc)
    {
        this.quantityAvailable = quantityAvailable;
        this.sku = sku;
        this.warehouses = warehouses;
        this.cost = cost;
        this.currencyCode = currencyCode;
        this.dscoCreateDate = dscoCreateDate;
        this.dscoItemId = dscoItemId;
        this.dscoLatCostUpdateDate = dscoLatCostUpdateDate;
        this.dscoLastQuantityUpdateDate = dscoLastQuantityUpdateDate;
        this.dscoLastUpdateDate = dscoLastUpdateDate;
        this.dscoSupplierId = dscoSupplierId;
        this.dscoSupplierName = dscoSupplierName;
        this.ean = ean;
        this.estimatedAvailabilityDate = estimatedAvailabilityDate;
        this.gtim = gtim;
        this.isbn = isbn;
        this.mpn = mpn;
        this.partnerSku = partnerSku;
        this.partnerSkuMap = partnerSkuMap;
        this.quanityOnOrder = quanityOnOrder;
        setStatus(status);
        this.tradingPartnerId = tradingPartnerId;
        this.tradingPartnerName = tradingPartnerName;
        this.upc = upc;
    }

    public int getQuantityAvailable()
    {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable)
    {
        this.quantityAvailable = quantityAvailable;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public List<ItemWarehouse> getWarehouses()
    {
        return warehouses;
    }

    public void setWarehouses(List<ItemWarehouse> warehouses)
    {
        this.warehouses = warehouses;
    }

    public Float getCost()
    {
        return cost;
    }

    public void setCost(Float cost)
    {
        this.cost = cost;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getDscoCreateDate()
    {
        return dscoCreateDate;
    }

    public void setDscoCreateDate(String dscoCreateDate)
    {
        this.dscoCreateDate = dscoCreateDate;
    }

    public String getDscoItemId()
    {
        return dscoItemId;
    }

    public void setDscoItemId(String dscoItemId)
    {
        this.dscoItemId = dscoItemId;
    }

    public String getDscoLatCostUpdateDate()
    {
        return dscoLatCostUpdateDate;
    }

    public void setDscoLatCostUpdateDate(String dscoLatCostUpdateDate)
    {
        this.dscoLatCostUpdateDate = dscoLatCostUpdateDate;
    }

    public String getDscoLastQuantityUpdateDate()
    {
        return dscoLastQuantityUpdateDate;
    }

    public void setDscoLastQuantityUpdateDate(String dscoLastQuantityUpdateDate)
    {
        this.dscoLastQuantityUpdateDate = dscoLastQuantityUpdateDate;
    }

    public String getDscoLastUpdateDate()
    {
        return dscoLastUpdateDate;
    }

    public void setDscoLastUpdateDate(String dscoLastUpdateDate)
    {
        this.dscoLastUpdateDate = dscoLastUpdateDate;
    }

    public String getDscoSupplierId()
    {
        return dscoSupplierId;
    }

    public void setDscoSupplierId(String dscoSupplierId)
    {
        this.dscoSupplierId = dscoSupplierId;
    }

    public String getDscoSupplierName()
    {
        return dscoSupplierName;
    }

    public void setDscoSupplierName(String dscoSupplierName)
    {
        this.dscoSupplierName = dscoSupplierName;
    }

    public String getEan()
    {
        return ean;
    }

    public void setEan(String ean)
    {
        this.ean = ean;
    }

    public String getEstimatedAvailabilityDate()
    {
        return estimatedAvailabilityDate;
    }

    public void setEstimatedAvailabilityDate(String estimatedAvailabilityDate)
    {
        this.estimatedAvailabilityDate = estimatedAvailabilityDate;
    }

    public String getGtim()
    {
        return gtim;
    }

    public void setGtim(String gtim)
    {
        this.gtim = gtim;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getMpn()
    {
        return mpn;
    }

    public void setMpn(String mpn)
    {
        this.mpn = mpn;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public List<PartnerSkuMap> getPartnerSkuMap()
    {
        return partnerSkuMap;
    }

    public void setPartnerSkuMap(List<PartnerSkuMap> partnerSkuMap)
    {
        this.partnerSkuMap = partnerSkuMap;
    }

    public Integer getQuanityOnOrder()
    {
        return quanityOnOrder;
    }

    public void setQuanityOnOrder(Integer quanityOnOrder)
    {
        this.quanityOnOrder = quanityOnOrder;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        if (status != null) {
            if (!status.equals("in-stock") && !status.equals("out-of-stock") && !status.equals("discontinued")) {
                throw new IllegalArgumentException("invalid status");
            }
        }
        this.status = status;
    }

    public String getTradingPartnerId()
    {
        return tradingPartnerId;
    }

    public void setTradingPartnerId(String tradingPartnerId)
    {
        this.tradingPartnerId = tradingPartnerId;
    }

    public String getTradingPartnerName()
    {
        return tradingPartnerName;
    }

    public void setTradingPartnerName(String tradingPartnerName)
    {
        this.tradingPartnerName = tradingPartnerName;
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
