package org.ninthfloor.bj21.lambda.v0;

import org.ninthfloor.bj21.gson.Table;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AddTable implements RequestHandler<APIGatewayProxyRequestEvent,
                                         APIGatewayProxyResponseEvent>
{
    final private GsonBuilder gsonBuilder = new GsonBuilder();

    private Gson gson = gsonBuilder.create();

    private final static Logger logger = LoggerFactory.getLogger(AddTable.class);

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)
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
        Table table = gson.fromJson(request.getBody(), Table.class);
        Map<String,String> headers = new HashMap<>();
        headers.put("Location",
                    "https://blackjack.dev/v0/tables/0");
        headers.put("Content-Type",
                    "application/json");
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setStatusCode(201);
        response.setHeaders(headers);
        response.setBody(gson.toJson(table));
        return response;
    }

}
