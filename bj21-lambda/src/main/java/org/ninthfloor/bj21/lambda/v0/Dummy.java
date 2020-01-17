package org.ninthfloor.bj21.lambda.v0;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dummy
    implements RequestHandler<APIGatewayProxyRequestEvent,
                              APIGatewayProxyResponseEvent>
{
    private final static Logger logger =
        LoggerFactory.getLogger(Dummy.class);

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
        logger.info("Responding with nothing");
        Map<String,String> headers = new HashMap<>();
        APIGatewayProxyResponseEvent response =
            new APIGatewayProxyResponseEvent();
        response.setHeaders(headers);
        return response;
    }
}
