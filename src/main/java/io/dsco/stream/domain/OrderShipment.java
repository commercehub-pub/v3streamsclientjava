package io.dsco.stream.domain;

import java.util.List;

public class OrderShipment
{
    private String dscoOrderId;
    private String poNumber;
    private List<ShipmentForUpdate> shipments;
    private String supplierOrderNumber;

    public OrderShipment(String dscoOrderId, String poNumber, List<ShipmentForUpdate> shipments, String supplierOrderNumber)
    {
        this.dscoOrderId = dscoOrderId;
        this.poNumber = poNumber;
        this.shipments = shipments;
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public String getDscoOrderId()
    {
        return dscoOrderId;
    }

    public void setDscoOrderId(String dscoOrderId)
    {
        this.dscoOrderId = dscoOrderId;
    }

    public String getPoNumber()
    {
        return poNumber;
    }

    public void setPoNumber(String poNumber)
    {
        this.poNumber = poNumber;
    }

    public List<ShipmentForUpdate> getShipments()
    {
        return shipments;
    }

    public void setShipments(List<ShipmentForUpdate> shipments)
    {
        this.shipments = shipments;
    }

    public String getSupplierOrderNumber()
    {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber)
    {
        this.supplierOrderNumber = supplierOrderNumber;
    }
}
