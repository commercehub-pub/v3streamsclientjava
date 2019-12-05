package io.dsco.stream.command.supplier;

import io.dsco.stream.command.Command;
import io.dsco.stream.domain.OrderShipment;
import io.dsco.stream.domain.ResponseSmallBatch;

import java.util.List;

public class CreateShipmentSmallBatch
implements Command<List<OrderShipment>, ResponseSmallBatch>
{
    @Override
    public ResponseSmallBatch execute(List<OrderShipment> param)
    throws Exception
    {
        //TODO
        return null;
    }
}
