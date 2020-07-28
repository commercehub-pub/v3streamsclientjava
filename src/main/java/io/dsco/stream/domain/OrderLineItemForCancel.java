package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class OrderLineItemForCancel {
	//MEMBERS
	/* The supplier of this item, Dsco's ID for it - one of the ID's must be present */
	private String dscoSupplierId;
	/* The supplier of this item, The trading partner's ID for it  - one of the ID's must be present */
	private String dscoTradingPartnerId;
	/* Dsco's unique ID for this item - one of the ID's must be present */
	private String dscoItemId;
	/* A unique ID for this item - one of the ID's must be present */
	private String sku;
	/* A unique ID for this item - one of the ID's must be present */
	private String upc;
	/* A unique ID for this item - one of the ID's must be present */
	private String ean;
	/* A unique ID for this item - one of the ID's must be present */
	private String partnerSku;
	/* The quantity of the line item to be cancelled */
	private int cancelledQuantity;
	private String cancelledReason;
	private String cancelCode;
	/* Uniquely identifies a line item for the order */
	private Integer lineNumber;

	//CONSTRUCTORS
	public OrderLineItemForCancel() {}

	//ACCESSORS / MUTATORS
	public String getDscoSupplierId() { return dscoSupplierId; }
	public void setDscoSupplierId(String val) { dscoSupplierId = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
	public String getDscoItemId() { return dscoItemId; }
	public void setDscoItemId(String val) { dscoItemId = val; }
	public String getSku() { return sku; }
	public void setSku(String val) { sku = val; }
	public String getUpc() { return upc; }
	public void setUpc(String val) { upc = val; }
	public String getEan() { return ean; }
	public void setEan(String val) { ean = val; }
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public int getCancelledQuantity() { return cancelledQuantity; }
	public void setCancelledQuantity(int val) { cancelledQuantity = val; }
	public String getCancelledReason() { return cancelledReason; }
	public void setCancelledReason(String val) { cancelledReason = val; }
	public String getCancelCode() { return cancelCode; }
	public void setCancelCode(String val) { cancelCode = val; }
	public Integer getLineNumber() { return lineNumber; }
	public void setLineNumber(Integer val) { lineNumber = val; }
}