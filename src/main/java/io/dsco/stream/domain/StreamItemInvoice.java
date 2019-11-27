package io.dsco.stream.domain;

public class StreamItemInvoice
extends StreamItem
{
    private Invoice invoice;

    public StreamItemInvoice(String id, Source source, Invoice invoice)
    {
        super(id, source);
        this.invoice = invoice;
    }

    @Override
    public String getKey()
    {
        return invoice.getInvoiceId();
    }

    public Invoice getInvoice()
    {
        return invoice;
    }
}
