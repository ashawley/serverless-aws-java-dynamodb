package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.gson.Table;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AddTableTest
{

    private AddTable addTable;

    private Table table;

    private APIGatewayProxyRequestEvent request;

    final private GsonBuilder gsonBuilder = new GsonBuilder();

    private Gson gson;

    @Mock
    private Context context;

    @Before
    public void init()
    {
        addTable = new AddTable();
        table = new Table();
        request = new APIGatewayProxyRequestEvent();
        gson = gsonBuilder.create();
    }

    @Test
    public void testHandleRequestFailure()
    {
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        // assertEquals(response, actual);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals("https://blackjack.dev/v0/tables/0",
                     response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals("null", response.getBody());
    }

    @Test
    public void testHandleRequestSuccess1()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        headers.put("Accept",
                    "application/json");
        request.setHttpMethod("POST");
        request.setResource("/v0/tables");
        request.setPath("");
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        table.setDecks(0L);
        table.setSeats(0L);
        table.setPlayers(0L);
        table.setMinimum(0L);
        table.setMaximum(0L);
        table.setRounds(0L);
        request.setBody(gson.toJson(table));
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals("https://blackjack.dev/v0/tables/0",
                     response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals(gson.toJson(table), response.getBody());
    }

    @Test
    public void testHandleRequestSuccess2()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        headers.put("",
                    "");
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        request.setBody("");
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        // assertEquals(response, actual);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals("https://blackjack.dev/v0/tables/0",
                     response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals("null", response.getBody());
    }

}
