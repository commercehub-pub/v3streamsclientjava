package io.dsco.demo;

import io.dsco.stream.domain.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A factory class to create domain objects needed by the demo. This allows the domain objects to remain "pure" and
 * independent of any particular scenario they might be used for.
 */
public class DomainFactory
{
    public static InvoiceLineItem invoiceLineItem(
            int quantity, String dscoItemId, String ean, String partnerSku, String sku, String upc,
            float retailerPrice)
    {
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the unique id columns must be present");
        }

        InvoiceLineItem item = new InvoiceLineItem();
        item.setQuantity(quantity);
        item.setDscoItemId(dscoItemId);
        item.setEan(ean);
        item.setPartnerSku(partnerSku);
        item.setSku(sku);
        item.setUpc(upc);
        item.setUnitPrice(retailerPrice);
        return item;
    }

    public static InvoiceShipInfo invoiceShipInfo(
            String date, String trackingNumber, String serviceLevelCode)
    {
        InvoiceShipInfo info = new InvoiceShipInfo();
        info.setDate(new Iso8601DateTime(date));
        info.setTrackingNumber(trackingNumber);
        info.setServiceLevelCode(serviceLevelCode);
        return info;
    }

    public static Invoice invoice(
            @NotNull String poNumber, @NotNull String invoiceId, float totalAmount,
            @NotNull List<InvoiceLineItem> lineItems, InvoiceShipInfo shipInfo)
    {
        Invoice invoice = new Invoice();
        invoice.setPoNumber(poNumber);
        invoice.setInvoiceId(invoiceId);
        invoice.setTotalAmount(totalAmount);
        invoice.setLineItems(lineItems);
        invoice.setShip(shipInfo);
        return invoice;
    }

    public static ShipmentLineItemForUpdate shipmentLineItemForUpdate(
            int quantity, String dscoItemId, String ean, int lineNumber, String partnerSku,
            String sku, String upc)
    {
        //one of the id fields is required
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the id fields is required");
        }

        ShipmentLineItemForUpdate sli = new ShipmentLineItemForUpdate();
        sli.setQuantity(quantity);
        sli.setDscoItemId(dscoItemId);
        sli.setEan(ean);
        sli.setLineNumber(lineNumber);
        sli.setPartnerSku(partnerSku);
        sli.setSku(sku);
        sli.setUpc(upc);
        return sli;
    }

    public static ShipmentForUpdate shipmentForUpdate(
            @NotNull List<ShipmentLineItemForUpdate> lineItems, @NotNull String trackingNumber)
    {
        ShipmentForUpdate s = new ShipmentForUpdate();
        s.setLineItems(lineItems);
        s.setTrackingNumber(trackingNumber);
        return s;
    }

    public static ShipmentsForUpdate shipmentsForUpdate(
            String dscoOrderId, String poNumber, List<ShipmentForUpdate> shipments, String supplierOrderNumber)
    {
        if (dscoOrderId == null && poNumber == null && supplierOrderNumber == null) {
            throw new IllegalArgumentException("one of dscoOrderId, poNumber, or supplierOrderNumber is required");
        }

        ShipmentsForUpdate s = new ShipmentsForUpdate();
        s.setDscoOrderId(dscoOrderId);
        s.setPoNumber(poNumber);
        s.setShipments(shipments);
        s.setSupplierOrderNumber(supplierOrderNumber);
        return s;
    }

    public static OrderId orderId(
            @NotNull String id, @NotNull OrderId.TYPE type, String supplierOrderNumber)
    {
        OrderId o = new OrderId();
        o.setId(id);
        o.setType(type);
        o.setSupplierOrderNumber(supplierOrderNumber);
        return o;
    }

    public static OrderLineItem orderLineItem(
            int quantity, int lineNumber, String dscoItemId, String ean, String partnerSku,
            String sku, String upc)
    {
        if (dscoItemId == null && ean == null && partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("at least one of the unique id fields must be present");
        }

        OrderLineItem oli = new OrderLineItem();
        oli.setQuantity(quantity);
        oli.setLineNumber(lineNumber);
        oli.setDscoItemId(dscoItemId);
        oli.setEan(ean);
        oli.setPartnerSku(partnerSku);
        oli.setSku(sku);
        oli.setUpc(upc);
        return oli;
    }

    public static OrderShipping orderShipping(
            @NotNull String address1, @NotNull String city, @NotNull String firstName,
            @NotNull String lastName, @NotNull String postal, @NotNull String region)
    {
        OrderShipping os = new OrderShipping();
        os.setAddress1(address1);
        os.setCity(city);
        os.setFirstName(firstName);
        os.setLastName(lastName);
        os.setPostal(postal);
        os.setRegion(region);
        return os;
    }

    public static OrderBillTo orderBillTo(
            @NotNull String address1, @NotNull String city, @NotNull String firstName,
            @NotNull String lastName, @NotNull String postal, @NotNull String region,
            @NotNull String country)
    {
        OrderBillTo b = new OrderBillTo();
        b.setAddress1(address1);
        b.setCity(city);
        b.setFirstName(firstName);
        b.setLastName(lastName);
        b.setPostal(postal);
        b.setRegion(region);
        b.setCountry(country);
        return b;
    }

    public static Order order(
            @NotNull List<OrderLineItem> lineItems, @NotNull String poNumber,
            @NotNull OrderShipping shipping, @NotNull OrderBillTo billTo,
            @NotNull String expectedDeliveryDate, @NotNull String shippingServiceLevelCode)
    {
        Order o = new Order();
        o.setLineItems(lineItems);
        o.setPoNumber(poNumber);
        o.setShipping(shipping);
        o.setBillTo(billTo);
        o.setExpectedDeliveryDate(new Iso8601DateTime(expectedDeliveryDate));
        o.setShippingServiceLevelCode(shippingServiceLevelCode);
        return o;
    }

    public static OrderLineItemForCancel orderLineItemForCancel(
            @NotNull String cancelCode, int cancelledQuantity, String cancelledReason,
            String dscoItemId, String dscoSupplierId, String dscoTradingPartnerId, String ean,
            Integer lineNumber, String partnerSku, String sku, String upc)
    {
        //one of the id fields must be present
        if (dscoItemId == null && dscoSupplierId == null && dscoTradingPartnerId == null && ean == null &&
                partnerSku == null && sku == null && upc == null) {
            throw new IllegalArgumentException("one of the id fields is required");
        }

        OrderLineItemForCancel o = new OrderLineItemForCancel();
        o.setCancelCode(cancelCode);
        o.setCancelledQuantity(cancelledQuantity);
        o.setCancelledReason(cancelledReason);
        o.setDscoItemId(dscoItemId);
        o.setDscoSupplierId(dscoSupplierId);
        o.setDscoTradingPartnerId(dscoTradingPartnerId);
        o.setEan(ean);
        o.setLineNumber(lineNumber);
        o.setPartnerSku(partnerSku);
        o.setSku(sku);
        o.setUpc(upc);
        return o;
    }

    public static OrderForCancel orderForCancel(
            @NotNull String id, @NotNull List<OrderLineItemForCancel> lineItems, @NotNull OrderForCancel.TYPE type)
    {
        OrderForCancel o = new OrderForCancel();
        o.setId(id);
        o.setLineItems(lineItems);
        o.setType(type);
        return o;
    }

    public static GetOrderById getOrderById(GetOrderById.ORDER_KEY orderKey, String value, String dscoAccountId, String dscoTradingPartnerId)
    {
        GetOrderById g = new GetOrderById();
        g.setOrderKey(orderKey);
        g.setValue(value);
        g.setDscoAccountId(dscoAccountId);
        g.setDscoTradingPartnerId(dscoTradingPartnerId);
        return g;
    }
}
