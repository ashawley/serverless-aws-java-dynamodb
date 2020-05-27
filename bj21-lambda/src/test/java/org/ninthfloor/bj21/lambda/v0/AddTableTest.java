package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.gson.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

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

@RunWith(MockitoJUnitRunner.class)
public class AddTableTest
{
    private AddTable addTable;

    private Table table;

    private APIGatewayProxyRequestEvent request;

    private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    private Gson gson;

    @Mock
    private Context context;

    @Before
    public void init()
    {
        ddb = DynamoDBEmbedded.create().amazonDynamoDB();
        addTable = new AddTable(ddb);
        table = new Table();
        request = new APIGatewayProxyRequestEvent();
        gson = gsonBuilder.create();

        List<AttributeDefinition> attrs =
            new ArrayList<AttributeDefinition>();
        attrs.add(new AttributeDefinition("key", ScalarAttributeType.S));
        attrs.add(new AttributeDefinition("id", ScalarAttributeType.N));

        List<KeySchemaElement> ks =
            new ArrayList<KeySchemaElement>();
        ks.add(new KeySchemaElement("key", KeyType.HASH));
        ks.add(new KeySchemaElement("id", KeyType.RANGE));

        ProvisionedThroughput throughput =
            new ProvisionedThroughput(1000L, 1000L);

        String tableName = "Tables";
        // The environment variable is probably empty, so override it.
        addTable.TABLES_TABLE_NAME = tableName;
        CreateTableResult res =
            createTable(ddb, tableName, attrs, ks, throughput);
    }

    @Test
    public void testHandleRequestFailure1()
    {
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertEquals("{\"message\":\"Invalid input\",\"file\":\"HTTP-body\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure2()
    {
        Map<String,String> queryStringParameters = new HashMap<>();
        table.setId(null);
        request.setBody(gson.toJson(table));
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertEquals("{\"message\":\"Invalid input\",\"file\":\"HTTP-body\"}",
                     response.getBody());
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
        request.setPath("/v0/tables");
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
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
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        request.setBody(gson.toJson(table));
        APIGatewayProxyResponseEvent response =
            addTable.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals("https://blackjack.dev/v0/tables/0",
                     response.getHeaders().get("Location"));
        assertEquals(gson.toJson(table), response.getBody());
    }

    private CreateTableResult createTable(
        AmazonDynamoDB ddb,
        String tableName,
        List<AttributeDefinition> attrs,
        List<KeySchemaElement> ks,
        ProvisionedThroughput throughput
    ) {

        CreateTableRequest request =
            new CreateTableRequest()
            .withTableName(tableName)
            .withAttributeDefinitions(attrs)
            .withKeySchema(ks)
            .withProvisionedThroughput(throughput);

        return ddb.createTable(request);
    }
}
