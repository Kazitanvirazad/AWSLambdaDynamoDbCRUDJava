package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.service.ProductService;
import com.serverless.util.ResponseObject;
import com.serverless.util.SerializeUtil;
import com.serverless.util.Validate;

import java.util.Map;

public class DropProductById implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final String PRODUCTINVENTORY_TABLE_PRIMARYKEY = System.getenv("PRODUCTINVENTORYTABLEPRIMARYKEY");

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        if (event.get("body") != null && !String.valueOf((String) event.get("body")).isEmpty()) {
            String body = String.valueOf((String) event.get("body"));
            Map<String, Object> requestBody = SerializeUtil.deserialize(body, Map.class);
            ResponseObject validateResult = Validate
                    .validatePrimaryKeyInRequestParameter(requestBody, PRODUCTINVENTORY_TABLE_PRIMARYKEY);
            if (!validateResult.isError()) {
                String productId = String.valueOf(validateResult.getData());
                if (ProductService.fetchProductById(productId) != null) {
                    boolean isProductItemDeleted = ProductService.deleteProductItemById(productId);
                    if (isProductItemDeleted) {
                        return ApiGatewayResponse.builder().setStatusCode(200).setHeaderContentTypeToJSON()
                                .setObjectBody(new ResponseObject(false, "Product deleted successfully!")).build();
                    }
                    return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                            .setObjectBody(new ResponseObject(true, "Failed to delete Product!")).build();
                }
                return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                        .setObjectBody(new ResponseObject(true, "Product doesn't exists with id: " + productId))
                        .build();

            }
            return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                    .setObjectBody(validateResult).build();
        }
        return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                .setObjectBody(new ResponseObject(true, "Invalid Request body JSON Schema!")).build();

    }
}
