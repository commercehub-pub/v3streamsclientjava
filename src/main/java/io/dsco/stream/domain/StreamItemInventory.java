package io.dsco.stream.domain;

public class StreamItemInventory
extends StreamItem
{
    private ItemInventory itemInventory;

    public StreamItemInventory(String id, Source source, ItemInventory itemInventory)
    {
        super(id, source);
        this.itemInventory = itemInventory;
    }

    @Override
    public String getKey()
    {
        return itemInventory.getSku();
    }

    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}
