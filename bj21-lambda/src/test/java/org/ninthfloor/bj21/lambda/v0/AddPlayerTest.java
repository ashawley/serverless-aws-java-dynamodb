package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.dynamodb.Players;
import org.ninthfloor.bj21.dynamodb.Tables;
import org.ninthfloor.bj21.gson.Player;
import org.ninthfloor.bj21.gson.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddPlayerTest
{
    private AddPlayer addPlayer;

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

    private APIGatewayProxyRequestEvent.ProxyRequestContext requestContext;

    @Before
    public void init()
    {
        ddb = DynamoDBEmbedded.create().amazonDynamoDB();
        addPlayer = new AddPlayer(ddb);
        player = new Player();
        table = new Table();
        request = new APIGatewayProxyRequestEvent();
        requestContext = new APIGatewayProxyRequestEvent.ProxyRequestContext();
        request.setRequestContext(requestContext);
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

        tableName = "Players";
        addPlayer.PLAYERS_TABLE_NAME = tableName;
        CreateTableResult res1 =
            createTable(ddb, tableName, attrs, ks, throughput);
        assertEquals(Long.valueOf(0L), res1.getTableDescription().getItemCount());
        players = new Players(tableName, ddb, gson);

        tableName = "Tables";
        addPlayer.TABLES_TABLE_NAME = tableName;
        CreateTableResult res2 =
            createTable(ddb, tableName, attrs, ks, throughput);
        assertEquals(Long.valueOf(0L), res2.getTableDescription().getItemCount());
        tables = new Tables(tableName, ddb, gson);
    }

    @Test
    public void testHandleRequestFailure1()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        request.setHttpMethod("POST");
        request.setPathParameters(pathParameters);
        APIGatewayProxyResponseEvent response =
            addPlayer.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(404), response.getStatusCode());
        assertEquals("{\"message\":\"Not found\",\"file\":\"POST /v0/tables/0\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure2()
    {
        Map<String,String> pathParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        request.setHttpMethod("POST");
        request.setPathParameters(pathParameters);
        table.setId(0L);
        tables.add(table);
        APIGatewayProxyResponseEvent response =
            addPlayer.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertNull(response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(405), response.getStatusCode());
        assertEquals("{\"message\":\"Invalid input\",\"file\":\"HTTP-body\"}",
                     response.getBody());
    }

    @Test
    public void testHandleRequestFailure3()
    {
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        table.setId(0L);
        tables.add(table);
        player.setId(null);
        request.setHttpMethod("POST");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setBody(gson.toJson(player));
        APIGatewayProxyResponseEvent response =
            addPlayer.handleRequest(request, context);
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
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        headers.put("Accept",
                    "application/json");
        headers.put("Host",
                    "blackjack.dev");
        pathParameters.put("tableId", "0");
        request.setHttpMethod("POST");
        request.setResource("/v0/tables/{tableId}/players");
        request.setPath("/v0/tables/0/players");
        requestContext.setPath("/dev/v0/tables/0/players");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        request.setBody(gson.toJson(player));
        APIGatewayProxyResponseEvent response =
            addPlayer.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals("https://blackjack.dev/dev/v0/tables/0/players/0",
                     response.getHeaders().get("Location"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals(gson.toJson(player), response.getBody());
        Optional<Player> player = players.getById(0L);
        assertTrue(player.isPresent());
    }

    @Test
    public void testHandleRequestSuccess2()
    {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> pathParameters = new HashMap<>();
        Map<String,String> queryStringParameters = new HashMap<>();
        pathParameters.put("tableId", "0");
        headers.put("Host", "");
        request.setHttpMethod("");
        request.setResource("");
        request.setPath("");
        requestContext.setPath("");
        request.setPathParameters(pathParameters);
        request.setQueryStringParameters(queryStringParameters);
        request.setHeaders(headers);
        table.setId(0L);
        tables.add(table);
        player.setId(0L);
        request.setBody(gson.toJson(player));
        APIGatewayProxyResponseEvent response =
            addPlayer.handleRequest(request, context);
        assertEquals("application/json",
                     response.getHeaders().get("Content-Type"));
        assertEquals(Integer.valueOf(201), response.getStatusCode());
        assertEquals(gson.toJson(player), response.getBody());
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
