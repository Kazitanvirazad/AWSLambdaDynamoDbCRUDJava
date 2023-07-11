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

public class ModifyProduct implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final String PRODUCTINVENTORY_TABLE_PRIMARYKEY = System.getenv("PRODUCTINVENTORYTABLEPRIMARYKEY");

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        if (event.get("body") != null && !String.valueOf((String) event.get("body")).isEmpty()) {
            String body = String.valueOf((String) event.get("body"));
            Map<String, Object> requestBody = SerializeUtil.deserialize(body, Map.class);
            ResponseObject validateResult = Validate.validateUpdateRequestParameters(requestBody, PRODUCTINVENTORY_TABLE_PRIMARYKEY);
            if (!validateResult.isError()) {
                boolean isAttributeKeyUpdateEligible = ProductDTO.getUpdateEligibleFieldNames()
                        .contains(requestBody.get("updateKey"));
                if (isAttributeKeyUpdateEligible) {
                    requestBody = (Map<String, Object>) validateResult.getData();
                    boolean updateResult = ProductService.updateProductById(String.valueOf(requestBody.get(PRODUCTINVENTORY_TABLE_PRIMARYKEY)),
                            String.valueOf(requestBody.get("updateKey")),
                            String.valueOf(requestBody.get("updateValue")));
                    if (updateResult) {
                        return ApiGatewayResponse.builder().setStatusCode(200).setHeaderContentTypeToJSON()
                                .setObjectBody(new ResponseObject(false,
                                        ProductService.fetchProductById(String.valueOf(requestBody.get(PRODUCTINVENTORY_TABLE_PRIMARYKEY)))))
                                .build();
                    }
                    return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                            .setObjectBody(new ResponseObject(true, "Data not updated!")).build();
                }
                return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                        .setObjectBody(
                                new ResponseObject(true,
                                        "Invalid attribute or selected attribute is not allowed to be modified!"))
                        .build();
            }
            return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                    .setObjectBody(validateResult).build();
        }
        return ApiGatewayResponse.builder().setStatusCode(404).setHeaderContentTypeToJSON()
                .setObjectBody(new ResponseObject(true, "Invalid Request body JSON Schema!")).build();
    }
}
