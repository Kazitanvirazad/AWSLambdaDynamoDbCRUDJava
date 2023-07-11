package com.serverless.util;

import com.serverless.service.ProductService;

import java.util.Map;

public class Validate {

    /**
     * Validates Database table primary key passed in the method argument and
     * returns response object with validation report.
     * ResponseObject contains an error boolean field, String message field and Object data field.
     * error field determines whether there is any error in the response,
     * message field holds appropriate message indicating the error,
     * data field holds the result data/object
     *
     * @param paramMap
     * @param primaryKey
     * @return {@link ResponseObject} with validation report
     */
    public static ResponseObject validatePrimaryKeyInRequestParameter(Map<String, Object> paramMap, String primaryKey) {
        ResponseObject key = new ResponseObject();
        if (paramMap != null && paramMap.containsKey(primaryKey)) {
            String primaryKeyValue = String.valueOf(paramMap.get(primaryKey));
            if (isValidString(primaryKeyValue)) {
                key.setError(false);
                key.setData(primaryKeyValue);
                return key;
            }
            key.setError(true);
            key.setMessage("Primary Key not found!");
            return key;
        }
        key.setError(true);
        key.setMessage("Invalid parameters!");
        return key;
    }

    /**
     * Validates String passed in the method argument with null check and length greater than zero
     *
     * @param str
     * @return true if it passes the validation or false
     */
    public static boolean isValidString(String str) {
        boolean isValidString = false;
        if (str != null && !str.isEmpty()) {
            isValidString = true;
        }
        return isValidString;
    }

    /**
     * Validates parameter map passed in the method argument and
     * returns response object with validation report.
     * ResponseObject contains an error boolean field, String message field and Object data field.
     * error field determines whether there is any error in the response,
     * message field holds appropriate message indicating the error,
     * data field holds the result data/object
     *
     * @param paramMap
     * @param primaryKey
     * @return {@link ResponseObject} with validation report
     */
    public static ResponseObject validateUpdateRequestParameters(Map<String, Object> paramMap, String primaryKey) {
        ResponseObject validatedResult = new ResponseObject();
        ResponseObject validatedPrimaryKey = validatePrimaryKeyInRequestParameter(paramMap, primaryKey);
        if (!validatedPrimaryKey.isError()) {
            if (ProductService.fetchProductById(String.valueOf(paramMap.get(primaryKey))) != null) {
                if (paramMap.containsKey("updateKey") && paramMap.containsKey("updateValue")
                        && isValidString(String.valueOf(paramMap.get("updateKey")))
                        && isValidString(String.valueOf(paramMap.get("updateValue")))) {
                    validatedResult.setError(false);
                    validatedResult.setData(paramMap);
                    return validatedResult;
                }
                validatedResult.setError(true);
                validatedResult.setMessage("Invalid or missing Update attributes!");
                return validatedResult;
            }
            validatedResult.setError(true);
            validatedResult.setMessage("Product doesn't exists with id: " + String.valueOf(paramMap.get(primaryKey)));
            return validatedResult;
        }
        return validatedPrimaryKey;
    }
}