package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.dto.ProductDTO;
import com.serverless.service.ProductService;
import com.serverless.util.ResponseObject;
import com.serverless.util.Validate;

import java.util.Map;

public class FetchProductById implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final String PRODUCTINVENTORY_TABLE_PRIMARYKEY = System.getenv("PRODUCTINVENTORYTABLEPRIMARYKEY");

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        Map<String, Object> queryParamMap = (Map<String, Object>) event.get("queryStringParameters");
        ResponseObject searchKey = Validate.validatePrimaryKeyInRequestParameter(queryParamMap, PRODUCTINVENTORY_TABLE_PRIMARYKEY);
        if (!searchKey.isError()) {
            String productId = (String) searchKey.getData();
            ProductDTO fetchedProduct = ProductService.fetchProductById(productId);
            if (fetchedProduct != null) {
                return ApiGatewayResponse.builder().setStatusCode(200)
                        .setObjectBody(new ResponseObject(false, fetchedProduct))
                        .setHeaderContentTypeToJSON()
                        .build();
            }
            return ApiGatewayResponse.builder().setStatusCode(404)
                    .setObjectBody(new ResponseObject(true, "Product Not found with id - " + productId))
                    .setHeaderContentTypeToJSON()
                    .build();
        }
        return ApiGatewayResponse.builder().setStatusCode(404)
                .setObjectBody(searchKey)
                .setHeaderContentTypeToJSON()
                .build();
    }
}
