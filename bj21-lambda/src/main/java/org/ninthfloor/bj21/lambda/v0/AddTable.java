package org.ninthfloor.bj21.lambda.v0;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddTable implements RequestHandler<APIGatewayProxyRequestEvent,
                                         APIGatewayProxyResponseEvent>
{

    private final static Logger logger = LoggerFactory.getLogger(AddTable.class);

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)
    {
        logger.info("Hello, world!");
	return new APIGatewayProxyResponseEvent();
    }

}
