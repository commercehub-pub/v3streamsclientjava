package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ItemInventory {
	//ENUMS
	public enum STATUS {
		@SerializedName("in-stock") IN_STOCK,
		@SerializedName("out-of-stock") OUT_OF_STOCK,
		@SerializedName("discontinued") DISCONTINUED,
		@SerializedName("hidden") HIDDEN
	}

	//MEMBERS
	/* The supplier-provided unique ID for the item; this value must be provided and must be unique amongst all items for the given supplier who owns the item */
	private String sku;
	/* Dsco's unique ID for this item */
	private String dscoItemId;
	/* Optional unique ID for this item */
	private String upc;
	/* Optional unique ID for this item */
	private String ean;
	/* Optional unique ID for this item */
	private String mpn;
	/* Optional unique ID for this item */
	private String isbn;
	/* Optional unique ID for this item */
	private String gtin;
	/* This value will not be set when used by a supplier.  Supplier's specify the partnerSkuMap.  For retailers this will be  the retailer-specific value provided by the supplier in the partnerSkuMap attribute. */
	private String partnerSku;
	private List<PartnerSkuMap> partnerSkuMap;
	/* Quantity may be provided within these objects for this item where each item represents the quantity at a specific warehouse; only one warehouse object must be present; soon quantity will only be allowed to be designated within the warehouse objecs embedded in this array */
	private List<ItemWarehouse> warehouses;
	/* If the partner so designates, this will be the sum of all quantity at all warehouses for this item.  Currently, this value must be provided and if warehouse quantity are provided must be the accurate sum of quantity across the designated warehouses.  In the future, this value will become read only as Dsco requires quantity to be specified at the warehouse level (see warehouses attribute) */
	private Integer quantityAvailable;
	/* The cost of the item */
	private Float cost;
	/* This value is read only and is derived from the quantityAvailable
attribute and the associated item's catalog.productStatus, one of these
values...
* **in-stock**: This state indicates that the item is in stock
  and the quantityAvailable value should be greater than zero

* **out-of-stock**: This state indicates that the item is out of stock and the availableQuantity
  value should be zero but it may change in the future

* **discontinued**: This state indicates that the item is discontinued.  Please reference the associated
  item's productStatus attribute which may drive changing this value and,
  if productStatus is used, may allow retailers to continue to sell the
  item even after the item is considered discontinued
  (productStatus.discontinued_sell_through status) */
	private STATUS status;
	/* The estimated availability date for the item inventory */
	private Iso8601DateTime estimatedAvailabilityDate;
	/* The quantity on order for the item */
	private Integer quantityOnOrder;
	/* The currency code of the cost */
	private String currencyCode;
	/* Dsco's unique ID for the supplier that owns this item */
	private String dscoSupplierId;
	/* Dsco's name for the supplier that owns this item */
	private String dscoSupplierName;
	/* The partner's unique ID for the supplier that owns this item */
	private String dscoTradingPartnerId;
	/* The partner's name for the supplier that owns this item */
	private String dscoTradingPartnerName;
	/* The date this item was created */
	private Iso8601DateTime dscoCreateDate;
	/* The date of the last quantity update */
	private Iso8601DateTime dscoLastQuantityUpdateDate;
	/* The date of the last cost update */
	private Iso8601DateTime dscoLastCostUpdateDate;
	/* The last date any attribute of the associated item was updated */
	private Iso8601DateTime dscoLastUpdateDate;

	//CONSTRUCTORS
	public ItemInventory() {}

	//ACCESSORS / MUTATORS
	public String getSku() { return sku; }
	public void setSku(String val) { sku = val; }
	public String getDscoItemId() { return dscoItemId; }
	public void setDscoItemId(String val) { dscoItemId = val; }
	public String getUpc() { return upc; }
	public void setUpc(String val) { upc = val; }
	public String getEan() { return ean; }
	public void setEan(String val) { ean = val; }
	public String getMpn() { return mpn; }
	public void setMpn(String val) { mpn = val; }
	public String getIsbn() { return isbn; }
	public void setIsbn(String val) { isbn = val; }
	public String getGtin() { return gtin; }
	public void setGtin(String val) { gtin = val; }
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public List<PartnerSkuMap> getPartnerSkuMap() { return partnerSkuMap; }
	public void setPartnerSkuMap(List<PartnerSkuMap> val) { partnerSkuMap = val; }
	public List<ItemWarehouse> getWarehouses() { return warehouses; }
	public void setWarehouses(List<ItemWarehouse> val) { warehouses = val; }
	public Integer getQuantityAvailable() { return quantityAvailable; }
	public void setQuantityAvailable(Integer val) { quantityAvailable = val; }
	public Float getCost() { return cost; }
	public void setCost(Float val) { cost = val; }
	public STATUS getStatus() { return status; }
	public void setStatus(STATUS val) { status = val; }
	public Iso8601DateTime getEstimatedAvailabilityDate() { return estimatedAvailabilityDate; }
	public void setEstimatedAvailabilityDate(Iso8601DateTime val) { estimatedAvailabilityDate = val; }
	public Integer getQuantityOnOrder() { return quantityOnOrder; }
	public void setQuantityOnOrder(Integer val) { quantityOnOrder = val; }
	public String getCurrencyCode() { return currencyCode; }
	public void setCurrencyCode(String val) { currencyCode = val; }
	public String getDscoSupplierId() { return dscoSupplierId; }
	public void setDscoSupplierId(String val) { dscoSupplierId = val; }
	public String getDscoSupplierName() { return dscoSupplierName; }
	public void setDscoSupplierName(String val) { dscoSupplierName = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
	public String getDscoTradingPartnerName() { return dscoTradingPartnerName; }
	public void setDscoTradingPartnerName(String val) { dscoTradingPartnerName = val; }
	public Iso8601DateTime getDscoCreateDate() { return dscoCreateDate; }
	public void setDscoCreateDate(Iso8601DateTime val) { dscoCreateDate = val; }
	public Iso8601DateTime getDscoLastQuantityUpdateDate() { return dscoLastQuantityUpdateDate; }
	public void setDscoLastQuantityUpdateDate(Iso8601DateTime val) { dscoLastQuantityUpdateDate = val; }
	public Iso8601DateTime getDscoLastCostUpdateDate() { return dscoLastCostUpdateDate; }
	public void setDscoLastCostUpdateDate(Iso8601DateTime val) { dscoLastCostUpdateDate = val; }
	public Iso8601DateTime getDscoLastUpdateDate() { return dscoLastUpdateDate; }
	public void setDscoLastUpdateDate(Iso8601DateTime val) { dscoLastUpdateDate = val; }
}