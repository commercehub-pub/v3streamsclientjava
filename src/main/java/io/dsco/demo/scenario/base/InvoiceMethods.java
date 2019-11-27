package io.dsco.demo.scenario.base;

import io.dsco.stream.api.StreamV3Api;
import io.dsco.stream.domain.StreamItemInvoice;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InvoiceMethods
{
    default List<StreamItemInvoice> getInvoiceEventsFromPosition(StreamV3Api streamV3Api, String streamId, String position, Logger logger)
    {
        List<StreamItemInvoice> items;

        try {
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("getting invoice events in stream {0} from position {1}", streamId, position));
            }

            CompletableFuture<HttpResponse<JsonNode>> future = streamV3Api.getStreamEventsFromPosition(streamId, position);

            if (logger.isDebugEnabled()) {
                logger.debug("waiting for response for stream events");
            }

            int httpStatus = future.get().getStatus();
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("http response code for stream events: {0}", httpStatus));
            }
            if (httpStatus != 200) {
                throw new IllegalStateException("got invalid http response when retrieving stream events: " + httpStatus);
            }

            JSONArray list = future.get().getBody().getArray();

            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("there are {0} items in the stream", list.length()));
            }

            //parse out each invoice (for demo purposes, just grab the invoiceId)
            //could use Gson to auto parse into the java domain objects
            items = new ArrayList<>(list.length());
            for (int i=0; i<list.length(); i++) {
                //TODO
            }

            return items;

        } catch (Exception e) {
            logger.error("unexpected error", e);
            return null;
        }
    }
}
