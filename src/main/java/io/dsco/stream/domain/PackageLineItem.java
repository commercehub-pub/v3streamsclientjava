package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PackageLineItem
{
    private int quantity;
    private String departmentId;
    private String departmentName;
    private String dscoItemId;
    private String ean;
    private Integer lineNumber;
    private String merchandisingAccountId;
    private String merchandisingAccountName;
    private Integer originalLineNumber;
    private Integer originalOrderQuantity;
    private String partnerSku;
    private List<String> retailerItemIds;
    private String sku;
    private String upc;

    public PackageLineItem() {}

    public PackageLineItem(
            int quantity, String departmentId, String departmentName, @NotNull String dscoItemId, String ean,
            Integer lineNumber, String merchandisingAccountId, String merchandisingAccountName, Integer originalLineNumber,
            Integer originalOrderQuantity, String partnerSku, List<String> retailerItemIds, String sku, String upc)
    {
        this.quantity = quantity;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.dscoItemId = dscoItemId;
        this.ean = ean;
        this.lineNumber = lineNumber;
        this.merchandisingAccountId = merchandisingAccountId;
        this.merchandisingAccountName = merchandisingAccountName;
        this.originalLineNumber = originalLineNumber;
        this.originalOrderQuantity = originalOrderQuantity;
        this.partnerSku = partnerSku;
        this.retailerItemIds = retailerItemIds;
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

    public String getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
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

    public String getMerchandisingAccountId()
    {
        return merchandisingAccountId;
    }

    public void setMerchandisingAccountId(String merchandisingAccountId)
    {
        this.merchandisingAccountId = merchandisingAccountId;
    }

    public String getMerchandisingAccountName()
    {
        return merchandisingAccountName;
    }

    public void setMerchandisingAccountName(String merchandisingAccountName)
    {
        this.merchandisingAccountName = merchandisingAccountName;
    }

    public Integer getOriginalLineNumber()
    {
        return originalLineNumber;
    }

    public void setOriginalLineNumber(Integer originalLineNumber)
    {
        this.originalLineNumber = originalLineNumber;
    }

    public Integer getOriginalOrderQuantity()
    {
        return originalOrderQuantity;
    }

    public void setOriginalOrderQuantity(Integer originalOrderQuantity)
    {
        this.originalOrderQuantity = originalOrderQuantity;
    }

    public String getPartnerSku()
    {
        return partnerSku;
    }

    public void setPartnerSku(String partnerSku)
    {
        this.partnerSku = partnerSku;
    }

    public List<String> getRetailerItemIds()
    {
        return retailerItemIds;
    }

    public void setRetailerItemIds(List<String> retailerItemIds)
    {
        this.retailerItemIds = retailerItemIds;
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
