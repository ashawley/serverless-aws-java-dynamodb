package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.dynamodb.Tables;
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
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetTableByIdTest
{
    private GetTableById getTableById;

    private Table table;

    private Tables tables;

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
        getTableById = new GetTableById(ddb);
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
        getTableById.TABLES_TABLE_NAME = tableName;
        CreateTableResult res =
            createTable(ddb, tableName, attrs, ks, throughput);

        tables = new Tables(tableName, ddb, gson);
    }

    @Test
    public void testHandleRequestFailure()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        request.setBody("");
        APIGatewayProxyResponseEvent response =
            getTableById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertEquals("{\"message\":\"Invalid input\",\"file\":\" \"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestSuccess1()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        headers.put("Accept",
                    "application/json");
        pathParameters.put("tableId", "0");
        request.setHttpMethod("GET");
        request.setResource("/v0/tables/{tableId}");
        request.setPath("/v0/tables/0");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        tables.add(table);
        APIGatewayProxyResponseEvent response =
            getTableById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(200), response.getStatusCode());
        assertEquals(gson.toJson(table),
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure2()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        pathParameters.put("tableId", "");
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        APIGatewayProxyResponseEvent response =
            getTableById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertNull(response.getHeaders().get("Location"));
        assertEquals("{\"message\":\"Invalid input\",\"file\":\" \"}",
                     response.getBody());
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
