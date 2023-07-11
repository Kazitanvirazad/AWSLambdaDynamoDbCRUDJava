package com.serverless.service;

import com.serverless.dto.ProductDTO;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    private static final String REGION = System.getenv("REGION");
    private static final String PRODUCTINVENTORY_TABLE_NAME = System.getenv("DYNAMODB_PRODUCTINVENTORYTABLE");
    private static final String PRODUCTINVENTORY_TABLE_PRIMARYKEY = System.getenv("PRODUCTINVENTORYTABLEPRIMARYKEY");

    /**
     * Saves the product object to the DynamoDB Database Table
     *
     * @param productDTO
     * @return boolean true if product is persisted in to the database else false
     */
    public static boolean saveProduct(ProductDTO productDTO) {
        boolean isProductSaved = false;
        try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.of(REGION)).build()) {
            PutItemRequest putItemRequest = PutItemRequest.builder().tableName(PRODUCTINVENTORY_TABLE_NAME)
                    .item(productDTO.createAttributeValueMap()).build();
            dynamoDbClient.putItem(putItemRequest);
            isProductSaved = true;
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return isProductSaved;
    }

    /**
     * Fetches ProductDTO from the database if exists or returns null
     *
     * @param {@code productId}
     * @return ProductDTO from the database if exists or null
     */
    public static ProductDTO fetchProductById(String productId) {
        ProductDTO product = null;
        try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.of(REGION)).build()) {
            Map<String, AttributeValue> productItemToFetch = new HashMap<>();
            productItemToFetch.put(PRODUCTINVENTORY_TABLE_PRIMARYKEY, AttributeValue.builder().s(productId).build());
            GetItemRequest getItemRequest = GetItemRequest.builder().tableName(PRODUCTINVENTORY_TABLE_NAME)
                    .key(productItemToFetch).build();
            Map<String, AttributeValue> getItemResponse = dynamoDbClient.getItem(getItemRequest).item();
            if (getItemResponse != null && !getItemResponse.isEmpty()) {
                product = ProductDTO.createProductDTOFromAttributeMap(getItemResponse);
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Fetches all products from DynamoDB database table and returns list of
     * products or returns an empty list if no Product exists in the database
     *
     * @return List<ProductDTO> from DynamoDB database table or empty list if no Product exists in the database
     */
    public static List<ProductDTO> fetchAllProducts() {
        List<ProductDTO> products = Collections.EMPTY_LIST;
        try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.of(REGION)).build()) {

            ScanRequest scanRequest = ScanRequest.builder().tableName(PRODUCTINVENTORY_TABLE_NAME).build();
            List<Map<String, AttributeValue>> scanItemResponse = dynamoDbClient.scan(scanRequest).items();
            if (scanItemResponse != null && !scanItemResponse.isEmpty()) {
                products = ProductDTO.createProductDTOFromAttributeMapList(scanItemResponse);
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Update the Product by Id if already exists and returns true or returns false
     *
     * @param primaryKeyValue
     * @param updateAttribute
     * @param updateAttributeValue
     * @return true if product gets updated or false if update gets failed
     */
    public static boolean updateProductById(String primaryKeyValue, String updateAttribute, String updateAttributeValue) {
        boolean isProductUpdated = false;
        try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.of(REGION)).build()) {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put(PRODUCTINVENTORY_TABLE_PRIMARYKEY, AttributeValue.builder().s(primaryKeyValue).build());
            Map<String, AttributeValueUpdate> attributeValueUpdateMap = new HashMap<>();
            attributeValueUpdateMap.put(updateAttribute, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(updateAttributeValue).build()).build());
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName(PRODUCTINVENTORY_TABLE_NAME).key(key).attributeUpdates(attributeValueUpdateMap)
                    .build();
            dynamoDbClient.updateItem(updateItemRequest).attributes();
            isProductUpdated = true;
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return isProductUpdated;
    }

    /**
     * Deletes Product with the id passed in the method argument from the database and returns true
     * or return false if deletion failed
     *
     * @param productId
     * @return true if Product Item gets deleted or false
     */
    public static boolean deleteProductItemById(String productId) {
        boolean isProductItemDeleted = false;
        try (DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.of(REGION)).build()) {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put(PRODUCTINVENTORY_TABLE_PRIMARYKEY, AttributeValue.builder().s(productId).build());
            DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                    .key(key).tableName(PRODUCTINVENTORY_TABLE_NAME).build();
            dynamoDbClient.deleteItem(deleteItemRequest);
            isProductItemDeleted = true;
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }

        return isProductItemDeleted;
    }

}
