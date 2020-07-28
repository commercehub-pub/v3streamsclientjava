package io.dsco.stream.domain;

import java.util.List;

@SuppressWarnings("unused")
public class ShipmentsForUpdate {
	//MEMBERS
	/* this or poNumber or supplierOrderNumber is required to identify the order this shipment is associated with */
	private String dscoOrderId;
	/* this or dscoOrderId or supplierOrderNumber is required to identify the order this shipment is associated with */
	private String poNumber;
	/* this or dscoOrderId or poNumber is required to identify the order this shipment is associated with */
	private String supplierOrderNumber;
	private List<ShipmentForUpdate> shipments;

	//CONSTRUCTORS
	public ShipmentsForUpdate() {}

	//ACCESSORS / MUTATORS
	public String getDscoOrderId() { return dscoOrderId; }
	public void setDscoOrderId(String val) { dscoOrderId = val; }
	public String getPoNumber() { return poNumber; }
	public void setPoNumber(String val) { poNumber = val; }
	public String getSupplierOrderNumber() { return supplierOrderNumber; }
	public void setSupplierOrderNumber(String val) { supplierOrderNumber = val; }
	public List<ShipmentForUpdate> getShipments() { return shipments; }
	public void setShipments(List<ShipmentForUpdate> val) { shipments = val; }
}