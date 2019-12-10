package io.dsco.stream.command.retailer;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.command.Command;
import io.dsco.stream.domain.StreamEvent;
import io.dsco.stream.shared.NetworkExecutor;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetAnyEventsFromPosition
implements Command<String, List<StreamEvent<?>>>
{

    public enum Type { UndeliverableShipment, Invoice, Cancelled, Shipped, Inventory } //, Order, OrderItemChange

    private static final Logger logger = LogManager.getLogger(GetAnyEventsFromPosition.class);
    private final Type type;
    private final StreamV3Api streamV3Api;
    private final String streamId;
    private final int partitionId;

    public GetAnyEventsFromPosition(Type type, StreamV3Api streamV3Api, String streamId, int partitionId)
    {
        this.type = type;
        this.streamV3Api = streamV3Api;
        this.streamId = streamId;
        this.partitionId = partitionId;
    }

    @Override
    public List<StreamEvent<?>> execute(String position) throws Exception
    {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("getting events in stream {0} from position {1}", streamId, position));
        }

        CompletableFuture<HttpResponse<JsonNode>> future  = NetworkExecutor.getInstance().execute((x) -> {
            return streamV3Api.getStreamEventsFromPosition(streamId, partitionId, position);
        }, streamV3Api, logger, "getAnyEventsFromPosition", NetworkExecutor.HTTP_RESPONSE_200);

        JSONArray resultsJsonArray = future.get().getBody().getArray();
        List<StreamEvent<?>> results = new ArrayList<>(resultsJsonArray.length());
        for (int i=0; i<resultsJsonArray.length(); i++) {
            JSONObject jsonObject = resultsJsonArray.getJSONObject(i);

            String id = jsonObject.getString("id");
            StreamEvent.Source source = StreamEvent.Source.valueOf(jsonObject.getString("source"));

            //grab just the payload json so it can be properly converted based on the type
            String jsonPayload = jsonObject.getJSONObject("payload").toString();

            switch (type)
            {
                case Invoice:
                    results.add(new StreamEvent.PayloadInvoiceForUpdateStreamEvent(id, source, null, jsonPayload));
                    break;

                case UndeliverableShipment:
                    results.add(new StreamEvent.PayloadUndeliverableStreamEvent(id, source, null, jsonPayload));
                    break;

                case Inventory:
                    results.add(new StreamEvent.PayloadItemInventoryStreamEvent(id, source, null, jsonPayload));
                    break;

                case Cancelled:
                    //TODO: OrderItemChanged object, with a status of cancelled.
                    //BUT hold off - API response and docs don't (yet) match
                    results.add(new StreamEvent.PayloadGeneric(id, source));

                case Shipped:
                    //TODO: OrderItemChanged object, with a status of shipped.
                    //BUT hold off - API response and docs don't (yet) match
                    results.add(new StreamEvent.PayloadGeneric(id, source));

                default:
                    logger.info(jsonPayload);

//                case Order:
//                    results.add(new StreamItem.PayloadOrderStreamItem(id, source, jsonPayload));
//                    break;
//
//                case OrderItemChange:
//                    break;
//
            }
        }

        return results;
    }
}
