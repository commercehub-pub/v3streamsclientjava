package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class PackageLineItem {
	//MEMBERS
	/* The number of this item in the package */
	private int quantity;
	/* The DSCO unique ID of the item, one of dscoItemId, sku, partnerSku, upc or ean is required */
	private String dscoItemId;
	/* The SKU of the item, one of dscoItemId, sku, partnerSku, upc or ean is required */
	private String sku;
	/* The partner SKU of the item, one of dscoItemId, sku, partnerSku, upc or ean is required */
	private String partnerSku;
	/* The UPC of the item, one of dscoItemId, sku, partnerSku, upc or ean is required */
	private String upc;
	/* The ean of te item, one of dscoItemId, sku, partnerSku, upc or ean is required */
	private String ean;
	private Integer lineNumber;
	private Integer originalLineNumber;
	private Integer originalOrderQuantity;
	private List<String> retailerItemIds;
	private String departmentId;
	private String departmentName;
	private String merchandisingAccountId;
	private String merchandisingAccountName;

	//CONSTRUCTORS
	public PackageLineItem() {}

	//ACCESSORS / MUTATORS
	public int getQuantity() { return quantity; }
	public void setQuantity(int val) { quantity = val; }
	public String getDscoItemId() { return dscoItemId; }
	public void setDscoItemId(String val) { dscoItemId = val; }
	public String getSku() { return sku; }
	public void setSku(String val) { sku = val; }
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public String getUpc() { return upc; }
	public void setUpc(String val) { upc = val; }
	public String getEan() { return ean; }
	public void setEan(String val) { ean = val; }
	public Integer getLineNumber() { return lineNumber; }
	public void setLineNumber(Integer val) { lineNumber = val; }
	public Integer getOriginalLineNumber() { return originalLineNumber; }
	public void setOriginalLineNumber(Integer val) { originalLineNumber = val; }
	public Integer getOriginalOrderQuantity() { return originalOrderQuantity; }
	public void setOriginalOrderQuantity(Integer val) { originalOrderQuantity = val; }
	public List<String> getRetailerItemIds() { return retailerItemIds; }
	public void setRetailerItemIds(List<String> val) { retailerItemIds = val; }
	public String getDepartmentId() { return departmentId; }
	public void setDepartmentId(String val) { departmentId = val; }
	public String getDepartmentName() { return departmentName; }
	public void setDepartmentName(String val) { departmentName = val; }
	public String getMerchandisingAccountId() { return merchandisingAccountId; }
	public void setMerchandisingAccountId(String val) { merchandisingAccountId = val; }
	public String getMerchandisingAccountName() { return merchandisingAccountName; }
	public void setMerchandisingAccountName(String val) { merchandisingAccountName = val; }
}