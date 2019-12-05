package io.dsco.stream.command.retailer;

import com.google.gson.Gson;
import io.dsco.stream.api.InvoiceV3Api;
import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamItemInvoice;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetInvoiceEventsFromPosition
implements Command<String, List<StreamItemInvoice>>
{
    private static final Logger logger = LogManager.getLogger(GetInvoiceEventsFromPosition.class);

    private final String streamId;
    private final StreamV3Api streamV3ApiRetailer;

    public GetInvoiceEventsFromPosition(String streamId, StreamV3Api streamV3ApiRetailer)
    {
        this.streamId = streamId;
        this.streamV3ApiRetailer = streamV3ApiRetailer;
    }

    @Override
    public List<StreamItemInvoice> execute(String position)
    throws Exception
    {
        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3ApiRetailer.getStreamEventsFromPosition(streamId, position);
        }, streamV3ApiRetailer, logger, "getInvoiceEventsFromPosition", NetworkExecutor.HTTP_RESPONSE_200);

        JSONArray list = future.get().getBody().getArray();

        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
        }

        StreamItemInvoice[] results = new Gson().fromJson(future.get().getBody().toString(), StreamItemInvoice[].class);
        return Arrays.asList(results);
    }
}
