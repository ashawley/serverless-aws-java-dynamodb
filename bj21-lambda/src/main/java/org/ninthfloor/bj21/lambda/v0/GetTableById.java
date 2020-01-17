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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetTableById
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    private Gson gson =
        gsonBuilder.create();

    // This field is mutable and is public.
    // This is used by the test suite,
    // and minimizes the number of environment variables.
    public String TABLES_TABLE_NAME =
        System.getenv("TABLES_TABLE_NAME");

    private final static Logger logger =
        LoggerFactory.getLogger(GetTableById.class);

    /**
     * Constructor for test suite.
     *
     * Not called by AWS Lambda.
     *
     * @param   ddb     AmazonDynamoDB
     */
    public GetTableById(AmazonDynamoDB ddb) {
        this.ddb = ddb;
    }

    /**
     * The default constructor for AWS Lambda.
     *
     * This is not called by the test suite.
     * This will auto-configure the {@link AmazonDynamoDB} connection.
     */
    public GetTableById() {
        this(AmazonDynamoDBClientBuilder.standard().build());
    }

    public APIGatewayProxyResponseEvent handleRequest(
        APIGatewayProxyRequestEvent request,
        Context context)
    {
        logger.info("HTTP method: {}", request.getHttpMethod());
        logger.info("Resource path: {}", request.getResource());
        logger.info("Request path: {}", request.getPath());
        logger.info("Request query: {}", request.getQueryStringParameters());
        if (request.getHeaders() == null) {
            logger.info("Request headers: {}",
                        request.getHeaders());
        } else {
            logger.info("Accept header: {}",
                        request.getHeaders().get("Accept"));
        }
        logger.info("Request body: {}", request.getBody());
        logger.error("Responding with a 501 error");
        Error error = new Error();
        error.setMessage("Not implemented");
        error.setFile(request.getHttpMethod() + " " + request.getPath());
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type",
                    "application/json");
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setStatusCode(501);
        response.setHeaders(headers);
        response.setBody(gson.toJson(error));
        return response;
    }
}