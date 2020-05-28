package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.dynamodb.Hands;
import org.ninthfloor.bj21.dynamodb.Players;
import org.ninthfloor.bj21.dynamodb.Tables;
import org.ninthfloor.bj21.gson.Hand;
import org.ninthfloor.bj21.gson.Player;
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
public class GetHandByIdTest
{
    private GetHandById getHandById;

    private Hand hand;

    private Hands hands;

    private Player player;

    private Players players;

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
        getHandById = new GetHandById(ddb);
        hand = new Hand();
        player = new Player();
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

        String tableName;

        tableName = "Hands";
        getHandById.HANDS_TABLE_NAME = tableName;
        CreateTableResult res0 =
            createTable(ddb, tableName, attrs, ks, throughput);
        hands = new Hands(tableName, ddb, gson);

        tableName = "Players";
        getHandById.PLAYERS_TABLE_NAME = tableName;
        CreateTableResult res1 =
            createTable(ddb, tableName, attrs, ks, throughput);
        players = new Players(tableName, ddb, gson);

        tableName = "Tables";
        getHandById.TABLES_TABLE_NAME = tableName;
        CreateTableResult res2 =
            createTable(ddb, tableName, attrs, ks, throughput);
        tables = new Tables(tableName, ddb, gson);
    }

    @Test
    public void testHandleRequestFailure1()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        pathParameters.put("seatId", "0");
        pathParameters.put("handId", "0");
        request.setHttpMethod("GET");
        request.setPathParameters(pathParameters);
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(404), response.getStatusCode());
        assertEquals("{\"message\":\"Not found\",\"file\":\"GET /v0/tables/0\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure2()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        pathParameters.put("seatId", "0");
        pathParameters.put("handId", "0");
        request.setHttpMethod("GET");
        request.setPathParameters(pathParameters);
        table.setId(0L);
        tables.add(table);
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(404), response.getStatusCode());
        assertEquals("{\"message\":\"Not found\",\"file\":\"GET /v0/tables/0/players/0\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure3()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        pathParameters.put("seatId", "0");
        pathParameters.put("handId", "0");
        request.setHttpMethod("GET");
        request.setPathParameters(pathParameters);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        players.add(player);
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(404), response.getStatusCode());
        assertEquals("{\"message\":\"Not found\",\"file\":\"GET /v0/tables/0/players/0/hands/0\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure4()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        pathParameters.put("seatId", "0");
        request.setHttpMethod("GET");
        request.setPathParameters(pathParameters);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        players.add(player);
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertEquals("{\"message\":\"Invalid input\",\"file\":\"GET /v0/tables/0/players/0/hands/\"}",
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
        pathParameters.put("seatId", "0");
        pathParameters.put("handId", "0");
        request.setHttpMethod("GET");
        request.setResource("/v0/tables/{tableId}/players/{seatId}/hands/{handId}");
        request.setPath("/v0/tables/0/players/0/hands/0");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        players.add(player);
        hand.setId(0L);
        hands.add(hand);
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(200), response.getStatusCode());
        assertEquals(gson.toJson(hand),
                     response.getBody());
    }

    @Test
    public void testHandleRequestSuccess2()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        pathParameters.put("seatId", "0");
        pathParameters.put("handId", "0");
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        players.add(player);
        hand.setId(0L);
        hands.add(hand);
        request.setBody("");
        APIGatewayProxyResponseEvent response =
            getHandById.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(200), response.getStatusCode());
        assertEquals(gson.toJson(hand),
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
