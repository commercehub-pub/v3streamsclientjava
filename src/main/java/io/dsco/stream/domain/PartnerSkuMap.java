package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class PartnerSkuMap {
	//MEMBERS
	/* The retailer-specific sku that needs to be mapped to the retailer
who wants this sku */
	private String partnerSku;
	/* Designate which retailer to use this sku for by either specifying
this value, Dsco's unique account ID for the retailer, or dscoTradingPartnerId;
one of the A attributes must be present */
	private String dscoRetailerId;
	/* Designate which retailer to use this sku for by either specifying
this value, the partner's unique ID for the retailer, or dscoRetailerId;
one of the A attributes must be present */
	private String dscoTradingPartnerId;

	//CONSTRUCTORS
	public PartnerSkuMap() {}

	//ACCESSORS / MUTATORS
	public String getPartnerSku() { return partnerSku; }
	public void setPartnerSku(String val) { partnerSku = val; }
	public String getDscoRetailerId() { return dscoRetailerId; }
	public void setDscoRetailerId(String val) { dscoRetailerId = val; }
	public String getDscoTradingPartnerId() { return dscoTradingPartnerId; }
	public void setDscoTradingPartnerId(String val) { dscoTradingPartnerId = val; }
}