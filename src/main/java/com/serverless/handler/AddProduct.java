package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.dto.ProductDTO;
import com.serverless.service.ProductService;
import com.serverless.util.ResponseObject;
import com.serverless.util.SerializeUtil;
import com.serverless.util.Validate;

import java.util.Map;

public class AddProduct implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        String body = String.valueOf((String) event.get("body"));
        ProductDTO product = SerializeUtil.deserialize(body, ProductDTO.class);

        if (product != null && Validate.isValidString(product.getProductId())) {
            if (ProductService.fetchProductById(product.getProductId()) == null) {
                boolean result = ProductService.saveProduct(product);
                return result ? ApiGatewayResponse.builder().setStatusCode(200).setHeaderContentTypeToJSON()
                        .setObjectBody(new ResponseObject(false, "Item added successfully!")).build() :
                        ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                                .setObjectBody(new ResponseObject(true, "Database not updated")).build();
            }
            return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                    .setObjectBody(new ResponseObject(true, "Product already exists!")).build();
        }
        return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                .setObjectBody(new ResponseObject(true, "Request JSON does not match a valid Product Schema")).build();
    }
}
