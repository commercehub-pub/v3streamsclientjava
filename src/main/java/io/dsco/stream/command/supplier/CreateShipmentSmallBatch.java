package io.dsco.stream.command.supplier;

import com.google.gson.Gson;
import io.dsco.demo.Util;
import io.dsco.stream.api.OrderV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.ShipmentsForUpdate;
import io.dsco.stream.domain.SyncUpdateResponse;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateShipmentSmallBatch
implements Command<List<ShipmentsForUpdate>, SyncUpdateResponse>
{
    private static final Logger logger = LogManager.getLogger(CreateShipmentSmallBatch.class);
    private final OrderV3Api orderV3Api;

    public CreateShipmentSmallBatch(OrderV3Api orderV3Api)
    {
        this.orderV3Api = orderV3Api;
    }

    @Override
    public SyncUpdateResponse execute(List<ShipmentsForUpdate> shipments)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return orderV3Api.createShipmentSmallBatch(shipments);
        }, orderV3Api, logger, "createShipmentSmallBatch", NetworkExecutor.HTTP_RESPONSE_202);
        logger.info(future.get().getBody());

        //if desired, you can do a loop and check the order until the shipping info shows up on it. for the demo,
        // the code will just move on

        return Util.gson().fromJson(future.get().getBody().toString(), SyncUpdateResponse.class);
    }
}
