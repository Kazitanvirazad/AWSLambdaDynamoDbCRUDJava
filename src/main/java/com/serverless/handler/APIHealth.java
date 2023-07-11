package com.serverless.handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.util.ResponseObject;

public class APIHealth implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        ResponseObject responseObject = new ResponseObject(false, "API is Live!");
        return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(responseObject).setHeaderContentTypeToJSON().build();
    }

}