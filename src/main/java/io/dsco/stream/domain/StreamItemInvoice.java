package io.dsco.stream.domain;

public class StreamItemInvoice
extends StreamItem
{
    private InvoiceForUpdate invoice;

    public StreamItemInvoice(String id, Source source, InvoiceForUpdate invoice)
    {
        super(id, source);
        this.invoice = invoice;
    }

    @Override
    public String getKey()
    {
        return invoice.getInvoiceId();
    }

    public InvoiceForUpdate getInvoice()
    {
        return invoice;
    }
}
