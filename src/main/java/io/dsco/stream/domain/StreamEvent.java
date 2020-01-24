package io.dsco.stream.domain;

import com.google.gson.Gson;

import java.util.List;

public abstract class StreamEvent<T>
{
    public enum Source { stream, sync }

    private String id; //the stream position
    private Source source;
    private String ownerId;
    private T payload;

    @Deprecated
    public StreamEvent(String id, Source source)
    {
        this.id = id;
        this.source = source;
    }

    public StreamEvent(String id, Source source, String ownerId, T payload)
    {
        this.id = id;
        this.source = source;
        this.payload = payload;
    }

    //a unique identifier for the object; what this is depends on the subtype
    abstract public String getKey();

    public String getId()
    {
        return id;
    }

    public Source getSource()
    {
        return source;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public T getPayload()
    {
        return payload;
    }

    static class PayloadUndeliverableShipment
    {
        public String eventUuid;
        public Order order;
        public Package shipment;
    }

    public static class PayloadUndeliverableStreamEvent
    extends StreamEvent<PayloadUndeliverableShipment>
    {
        public PayloadUndeliverableStreamEvent(String id, Source source, String ownerId, String payloadJson)
        {
            super(id, source, ownerId, new Gson().fromJson(payloadJson, PayloadUndeliverableShipment.class));
        }

        @Override
        public String getKey()
        {
            return getPayload().eventUuid;
        }
    }

    static class PayloadInvoiceForUpdate
    {
        public String invoiceId;
        public float totalAmount;
        public String buyerId;
        public List<InvoiceCharge> charges;
        public String consumerOrderNumber;
        public String currencyCode;
        public String dscoOrderId;
        public Float expectedOrderTotalAmount;
        public Float expectedOrderTotalDifference;
        public String externalBatchId;
        public Float freightAmount;
        public Float handlingAmount;
        public String invoiceDate; //iso8601
        public List<InvoiceLineItemForUpdate> lineItems;
        public Float lineItemsSubtotal;
        public Integer numberOfLineItems;
        public String poNumber;
        public Float salesTaxAmount;
        public String sellerId;
        public String sellerInvoiceNumber;
        public InvoiceShipInfo ship;
        public InvoiceShipFromTo shipFrom;
        public InvoiceShipFromTo shipTo;
        public Float subtotalExcludingLineItems;
        public String supplierOrderNumber;
        public InvoiceTerms terms;
    }

    public static class PayloadInvoiceForUpdateStreamEvent
    extends StreamEvent<PayloadInvoiceForUpdate>
    {
        public PayloadInvoiceForUpdateStreamEvent(String id, Source source, String ownerId, String payloadJson)
        {
            super(id, source, ownerId, new Gson().fromJson(payloadJson, PayloadInvoiceForUpdate.class));
        }

        @Override
        public String getKey()
        {
            return getPayload().invoiceId;
        }
    }

    public static class PayloadOrderStreamEvent
    extends StreamEvent<Order>
    {
        public PayloadOrderStreamEvent(String id, Source source, String ownerId, String payloadJson)
        {
            super(id, source, ownerId, new Gson().fromJson(payloadJson, Order.class));
        }

        @Override
        public String getKey()
        {
            return getPayload().getDscoOrderId();
        }
    }

    public static class PayloadItemInventoryStreamEvent
    extends StreamEvent<ItemInventory>
    {
        public PayloadItemInventoryStreamEvent(String id, Source source, String ownerId, String payloadJson)
        {
            super(id, source, ownerId, new Gson().fromJson(payloadJson, ItemInventory.class));
        }

        @Override
        public String getKey()
        {
            return getPayload().getDscoItemId();
        }
    }

    public static class PayloadGeneric
    extends StreamEvent<String>
    {
        public PayloadGeneric(String id, Source source)
        {
            super(id, source, null, "{\"payload\": \"Test Payload\"}");
        }

        @Override
        public String getKey()
        {
            return "abc-123";
        }
    }

}
