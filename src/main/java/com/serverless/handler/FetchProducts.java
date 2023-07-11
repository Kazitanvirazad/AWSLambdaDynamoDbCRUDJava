package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.dto.ProductDTO;
import com.serverless.service.ProductService;
import com.serverless.util.ResponseObject;

import java.util.List;
import java.util.Map;

public class FetchProducts implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        List<ProductDTO> products = ProductService.fetchAllProducts();
        if (!products.isEmpty()) {
            return ApiGatewayResponse.builder().setStatusCode(200)
                    .setObjectBody(new ResponseObject(false, products))
                    .setHeaderContentTypeToJSON()
                    .build();
        }
        return ApiGatewayResponse.builder().setStatusCode(404)
                .setObjectBody(new ResponseObject(false, "No products found!"))
                .setHeaderContentTypeToJSON()
                .build();
    }
}
