package org.ninthfloor.bj21.lambda.v0;

import java.util.HashMap;
import java.util.Map;

import org.ninthfloor.bj21.gson.Error;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFound
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    final private GsonBuilder gsonBuilder =
        new GsonBuilder();

    final private Gson gson =
        gsonBuilder.create();

    private final static Logger logger =
        LoggerFactory.getLogger(NotFound.class);

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
        logger.info("Responding with 404 error");
        Error error = new Error();
        error.setMessage("Not found");
        error.setFile(request.getPath());
        Map<String,String> headers = new HashMap<>();
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setHeaders(headers);
        response.setBody(gson.toJson(error));
        return response;
    }
}
