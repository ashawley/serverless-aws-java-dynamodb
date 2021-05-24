package org.ninthfloor.bj21.lambda.v0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ninthfloor.bj21.dynamodb.TableItem;
import org.ninthfloor.bj21.dynamodb.Tables;
import org.ninthfloor.bj21.gson.Error;
import org.ninthfloor.bj21.gson.Table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.github.jknack.handlebars.Handlebars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddTable
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    final private AmazonDynamoDB ddb;

    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    final private Gson gson =
        gsonBuilder.create();

    final private Handlebars handlebars =
        new Handlebars();

    // This field is mutable and is public.
    // This is used by the test suite,
    // and minimizes the number of environment variables.
    public String TABLES_TABLE_NAME =
        System.getenv("TABLES_TABLE_NAME");

    private final static Logger logger =
        LoggerFactory.getLogger(AddTable.class);

    /**
     * Constructor for test suite.
     *
     * Not called by AWS Lambda.
     *
     * @param   ddb     AmazonDynamoDB
     */
    public AddTable(AmazonDynamoDB ddb) {
        this.ddb = ddb;
    }

    /**
     * The default constructor for AWS Lambda.
     *
     * This is not called by the test suite.
     * This will auto-configure the {@link AmazonDynamoDB} connection.
     */
    public AddTable() {
        this(AmazonDynamoDBClientBuilder.standard().build());
    }

    /**
     * Mapping for parameters as a POJO.
     */
    private class UriParameters {
        private Map<String,String> params;
        public UriParameters(Map<String,String> params) {
            this.params = params;
        }
        @SuppressWarnings("unused")
        public String getSchema() {
            return params.get("schema");
        }
        @SuppressWarnings("unused")
        public String getHost() {
            return params.get("host");
        }
        @SuppressWarnings("unused")
        public String getPath() {
            return params.get("path");
        }
        @SuppressWarnings("unused")
        public String getTableId() {
            return params.get("tableId");
        }
    }

    /**
     * Factory for POJO from parameters map.
     *
     * @param   params  Map&lt;String,String&gt;
     * @return  UriParameters
     */
    private UriParameters fromMap(Map<String,String> params) {
        return new UriParameters(params);
    }

    /**
     * Format uri with parameters.
     *
     * @param   handlebars      Handlebars
     * @param   uriTemplate    String
     * @param   params          Map&lt;String,String&gt;
     * @return  String
     */
    private String format(Handlebars handlebars, String uriTemplate,
                          Map<String,String> params) {
        try {
            return handlebars
                .compileInline(uriTemplate)
                .apply(fromMap(params));
        } catch (IOException e) {
            logger.error("Unable to format error: {}", e);
            return uriTemplate; // ???
        }
    }

    public APIGatewayProxyResponseEvent handleRequest(
        APIGatewayProxyRequestEvent request,
        Context context)
    {
        logger.info("HTTP method: {}", request.getHttpMethod());
        logger.info("Resource path: {}", request.getResource());
        logger.info("Request path: {}", request.getPath());
        logger.info("Account id: {}", request.getRequestContext().getAccountId());
        logger.info("Api id: {}", request.getRequestContext().getApiId());
        logger.info("Context path: {}", request.getRequestContext().getPath());
        logger.info("Path parameters: {}", request.getPathParameters());
        logger.info("Request query: {}", request.getQueryStringParameters());
        if (request.getHeaders() == null) {
            logger.info("Request headers: {}",
                        request.getHeaders());
        } else {
            logger.info("Accept header: {}",
                        request.getHeaders().get("Accept"));
            logger.info("Content-Type header: {}",
                        request.getHeaders().get("Content-Type"));
            logger.info("Host header: {}",
                        request.getHeaders().get("Host"));
        }
        logger.info("Request body: {}", request.getBody());
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
        } else if (table.getId() == null) {
            logger.error("Missing required id field in JSON");
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

        Tables tables = new Tables(TABLES_TABLE_NAME, ddb, gson);
        Item item = tables.add(table);
        TableItem tableItem = TableItem.fromItem(item);

        Map<String,String> headers = new HashMap<>();
        Map<String,String> uriParameters = new HashMap<>();
        uriParameters.put("schema", "https");
        uriParameters.put("host", request.getHeaders().get("Host")); // !
        uriParameters.put("path", request.getRequestContext().getPath());
        uriParameters.put("tableId", table.getId().toString());
        String location =
            format(handlebars,
                   "{{schema}}://{{host}}{{path}}/{{tableId}}",
                   uriParameters);
        headers.put("Location", location);
        headers.put("Content-Type",
                    "application/json");
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setStatusCode(201);
        response.setHeaders(headers);
        response.setBody(gson.toJson(tableItem.toGSON()));
        return response;
    }
}
