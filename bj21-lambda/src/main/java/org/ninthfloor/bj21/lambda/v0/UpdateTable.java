package org.ninthfloor.bj21.lambda.v0;

import java.util.HashMap;
import java.util.Map;

import org.ninthfloor.bj21.dynamodb.Tables;
import org.ninthfloor.bj21.gson.Error;
import org.ninthfloor.bj21.gson.Table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTable
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    final private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    final private Gson gson =
        gsonBuilder.create();

    // This field is mutable and is public.
    // This is used by the test suite,
    // and minimizes the number of environment variables.
    public String TABLES_TABLE_NAME =
        System.getenv("TABLES_TABLE_NAME");

    private final static Logger logger =
        LoggerFactory.getLogger(UpdateTable.class);

    /**
     * Constructor for test suite.
     *
     * Not called by AWS Lambda.
     *
     * @param   ddb     AmazonDynamoDB
     */
    public UpdateTable(AmazonDynamoDB ddb) {
        this.ddb = ddb;
    }

    /**
     * The default constructor for AWS Lambda.
     *
     * This is not called by the test suite.
     * This will auto-configure the {@link AmazonDynamoDB} connection.
     */
    public UpdateTable() {
        this(AmazonDynamoDBClientBuilder.standard().build());
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
        logger.info("Request body: {}", request.getBody());
        if (!StringUtils.isNumeric(request.getPathParameters().get("tableId"))) {
            logger.error("Responding with a 405 error");
            Error error = new Error();
            error.setMessage("Invalid input");
            error.setFile(request.getHttpMethod() + " " + request.getPath());
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

        Long tableId = Long.valueOf(request.getPathParameters().get("tableId"));
        Table table = gson.fromJson(request.getBody(), Table.class);

        if (table == null) {
            logger.error("Failed to deserialize JSON in request body");
            Error error = new Error();
            error.setMessage("Invalid input");
            error.setFile("HTTP-body");
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type",
                        "application/json");
            APIGatewayProxyResponseEvent response =
                new APIGatewayProxyResponseEvent();
            response.setStatusCode(405);
            response.setHeaders(headers);
            response.setBody(gson.toJson(error));
            return response;
        } else if (table.getId() != null && table.getId() != tableId) {
            logger.error("The id field in JSON didn't match the path");
            Error error = new Error();
            error.setMessage("Invalid input");
            error.setFile("HTTP-body");
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

        table.setId(tableId);
        Tables tables = new Tables(TABLES_TABLE_NAME, ddb, gson);
        tables.update(table);

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type",
                    "application/json");
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setStatusCode(200); // FIXME: 201 if created
        response.setHeaders(headers);
        response.setBody(gson.toJson(table));
        return response;
    }
}
