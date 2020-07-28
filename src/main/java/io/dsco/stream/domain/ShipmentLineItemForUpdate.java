package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class ShipmentLineItemForUpdate {
	//MEMBERS
	/* The number of this item in the shipment */
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

	//CONSTRUCTORS
	public ShipmentLineItemForUpdate() {}

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
}