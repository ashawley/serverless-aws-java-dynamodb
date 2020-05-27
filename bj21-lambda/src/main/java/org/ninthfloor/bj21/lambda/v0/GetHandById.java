package org.ninthfloor.bj21.lambda.v0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ninthfloor.bj21.dynamodb.Hands;
import org.ninthfloor.bj21.dynamodb.Players;
import org.ninthfloor.bj21.dynamodb.Tables;
import org.ninthfloor.bj21.gson.Error;
import org.ninthfloor.bj21.gson.Hand;
import org.ninthfloor.bj21.gson.Player;
import org.ninthfloor.bj21.gson.Table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.github.jknack.handlebars.Handlebars;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetHandById
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    final private Gson gson =
        gsonBuilder.create();

    final private Handlebars handlebars =
        new Handlebars();

    // These fields are mutable and public.
    // They are used by the test suite,
    // minimizing the need of environment variables.
    public String HANDS_TABLE_NAME =
        System.getenv("HANDS_TABLE_NAME");
    public String PLAYERS_TABLE_NAME =
        System.getenv("PLAYERS_TABLE_NAME");
    public String TABLES_TABLE_NAME =
        System.getenv("TABLES_TABLE_NAME");

    private final static Logger logger =
        LoggerFactory.getLogger(GetHandById.class);

    /**
     * Constructor for test suite.
     *
     * Not called by AWS Lambda.
     *
     * @param   ddb     AmazonDynamoDB
     */
    public GetHandById(AmazonDynamoDB ddb) {
        this.ddb = ddb;
    }

    /**
     * The default constructor for AWS Lambda.
     *
     * This is not called by the test suite.
     * This will auto-configure the {@link AmazonDynamoDB} connection.
     */
    public GetHandById() {
        this(AmazonDynamoDBClientBuilder.standard().build());
    }

    /**
     * Mapping for parameters as a POJO.
     */
    public class PathParameters {
        private Map<String,String> params;
        public PathParameters(Map<String,String> params) {
            this.params = params;
        }
        public String getTableId() {
            return params.get("tableId");
        }
        public String getSeatId() {
            return params.get("seatId");
        }
        public String getHandId() {
            return params.get("handId");
        }
    }

    /**
     * Factory for POJO from parameters map.
     *
     * @param   params  Map&lt;String,String&gt;
     * @return  PathParameters
     */
    public PathParameters fromMap(Map<String,String> params) {
        return new PathParameters(params);
    }

    /**
     * Format resource path with parameters.
     *
     * @param   handlebars      Handlebars
     * @param   pathTemplate    String
     * @param   params          Map&lt;String,String&gt;
     * @return  String
     */
    public String format(Handlebars handlebars, String pathTemplate,
                         Map<String,String> params) {
        try {
            return handlebars
                .compileInline(pathTemplate)
                .apply(fromMap(params));
        } catch (IOException e) {
            logger.error("Unable to format error: {}", e);
            return pathTemplate; // ???
        }
    }

    public APIGatewayProxyResponseEvent handleRequest(
        APIGatewayProxyRequestEvent request,
        Context context)
    {
        logger.info("HTTP method: {}", request.getHttpMethod());
        logger.info("Resource path: {}", request.getResource());
        logger.info("Request path: {}", request.getPath());
        logger.info("Path parameters: {}", request.getPathParameters());
        logger.info("Request query: {}", request.getQueryStringParameters());
        if (request.getHeaders() == null) {
            logger.info("Request headers: {}",
                        request.getHeaders());
        } else {
            logger.info("Accept header: {}",
                        request.getHeaders().get("Accept"));
        }

        String tableIdParam = request.getPathParameters().get("tableId");
        if (!StringUtils.isNumeric(tableIdParam)) {
            logger.error("Responding with a 405 error");
            Error error = new Error();
            error.setMessage("Invalid input");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(405);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        Long tableId = Long.valueOf(tableIdParam);
        Tables tables = new Tables(TABLES_TABLE_NAME, ddb, gson);
        Optional<Table> table = tables.getById(tableId);
        if (!table.isPresent()) {
            logger.error("Responding with a 404 error");
            Error error = new Error();
            error.setMessage("Not found");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(404);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        String seatIdParam = request.getPathParameters().get("seatId");
        if (!StringUtils.isNumeric(seatIdParam)) {
            logger.error("Responding with a 405 error");
            Error error = new Error();
            error.setMessage("Invalid input");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}/players/{{seatId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(405);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        Long seatId = Long.valueOf(seatIdParam);
        Players players = new Players(PLAYERS_TABLE_NAME, ddb, gson);
        Optional<Player> player = players.getById(seatId);
        if (!player.isPresent()) {
            logger.error("Responding with a 404 error");
            Error error = new Error();
            error.setMessage("Not found");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}/players/{{seatId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(404);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        String handIdParam = request.getPathParameters().get("handId");
        if (!StringUtils.isNumeric(handIdParam)) {
            logger.error("Responding with a 405 error");
            Error error = new Error();
            error.setMessage("Invalid input");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}/players/{{seatId}}/hands/{{handId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(405);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        Long handId = Long.valueOf(handIdParam);
        Hands hands = new Hands(HANDS_TABLE_NAME, ddb, gson);
        Optional<Hand> hand = hands.getById(handId);
        if (!hand.isPresent()) {
            logger.error("Responding with a 404 error");
            Error error = new Error();
            error.setMessage("Not found");
            String path =
                format(handlebars,
                       "/v0/tables/{{tableId}}/players/{{seatId}}/hands/{{handId}}",
                       request.getPathParameters());
            error.setFile(request.getHttpMethod() + " " + path);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(404);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } // else

        logger.info("Request body: {}", request.getBody());
        logger.info("Responding with a 200 error");
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type",
                    "application/json");
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setHeaders(headers);
        response.setBody(gson.toJson(table.get()));
        return response;
    }
}
