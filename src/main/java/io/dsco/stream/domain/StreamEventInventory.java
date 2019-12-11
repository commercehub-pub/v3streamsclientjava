package io.dsco.stream.domain;

//TODO: this should move into the StreamEvent class like the others
public class StreamEventInventory
extends StreamEvent
{
    private ItemInventory itemInventory;

    public StreamEventInventory(String id, Source source, ItemInventory itemInventory)
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
