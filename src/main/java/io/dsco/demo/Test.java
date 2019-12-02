package io.dsco.demo;

import com.google.gson.Gson;
import io.dsco.stream.domain.ResponseInvoiceChangeLog;

public class Test
{
    public static void main(String[] args)
    {
        String json = "{\"logs\":[{\"dateProcessed\":\"2019-12-02T17:36:13.604Z\",\"payload\":{\"charges\":[{\"amount\":1,\"title\":\"something line item\"}],\"invoiceId\":\"2019-12-02e\",\"lineItems\":[{\"quantity\":1,\"sku\":\"37d87b2e-9a14-4d6e-b83f-0877ac092b73\"}],\"totalAmount\":1},\"processId\":\"f2b8c138-e32a-5bdf-bedb-9767aeed35ee\",\"requestId\":\"3a6a6c4e-88f5-4547-af7f-da4dba76ba72\",\"requestMethod\":\"api\",\"requestMethodDetail\":{\"action\":\"DATA_NOT_FOUND\",\"end_time_ms\":1575308173605,\"messages\":[{\"message\":\"Invoice data not found\",\"type\":\"RECORD_ERROR\"}],\"start_time_ms\":1575308173599},\"results\":[{\"code\":\"general_failure\",\"description\":\"Update Invoice\",\"severity\":\"error\"}],\"status\":\"failure\"}]}";
        ResponseInvoiceChangeLog response = new Gson().fromJson(json, ResponseInvoiceChangeLog.class);
        System.out.println("response: " + response);
    }
}
