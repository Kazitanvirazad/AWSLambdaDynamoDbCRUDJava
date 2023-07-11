package com.serverless.dto;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ProductDTO implements Serializable {
    private static final long serialVersionUID = -8047608822492206073L;
    private String productId;
    private float price;
    private String color;
    private String productName;

    private String inventory;
    private PropertiesDTO properties;

    public ProductDTO() {
        super();
    }

    /**
     * Creates AttributeValue Map from this ProductDTO object
     *
     * @return Map<String, AttributeValue> from this ProductDTO object
     */
    public Map<String, AttributeValue> createAttributeValueMap() {
        Map<String, AttributeValue> productItem = new HashMap<>();
        productItem.put("productId", AttributeValue.builder().s(this.productId).build());
        productItem.put("price", AttributeValue.builder().s(String.valueOf(this.price)).build());
        if (this.color != null)
            productItem.put("color", AttributeValue.builder().s(this.color).build());
        if (this.productName != null)
            productItem.put("productName", AttributeValue.builder().s(this.productName).build());
        if (this.inventory != null)
            productItem.put("inventory", AttributeValue.builder().s(this.inventory).build());
        if (this.properties != null)
            productItem.put("properties", AttributeValue.builder().m(this.getProperties().createAttributeValueMap()).build());
        return productItem;
    }

    /**
     * Creates ProductDTO object from attributeValueMap
     *
     * @param attributeValueMap
     * @return ProductDTO
     */
    public static ProductDTO createProductDTOFromAttributeMap(Map<String, AttributeValue> attributeValueMap) {
        return new ProductDTO(attributeValueMap.get("price") != null ? attributeValueMap.get("productId").s() : null,
                attributeValueMap.get("price") != null ? Float.valueOf(attributeValueMap.get("price").s()) : null,
                attributeValueMap.get("color") != null ? attributeValueMap.get("color").s() : null,
                attributeValueMap.get("productName") != null ? attributeValueMap.get("productName").s() : null,
                attributeValueMap.get("inventory") != null ? attributeValueMap.get("inventory").s() : null,
                attributeValueMap.get("properties") != null ? PropertiesDTO.createPropertiesDTOFromAttributeMap(attributeValueMap.get("properties").m()) : null);
    }

    /**
     * Returns List<ProductDTO> from list of AttributeValue map
     *
     * @param attributeValueMapList
     * @return List<ProductDTO> from {@link AttributeValue map} list
     */
    public static List<ProductDTO> createProductDTOFromAttributeMapList(List<Map<String, AttributeValue>> attributeValueMapList) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Map<String, AttributeValue> attributeValueMap : attributeValueMapList) {
            productDTOList.add(createProductDTOFromAttributeMap(attributeValueMap));
        }
        return productDTOList;
    }

    /**
     * Returns list of field names which are eligible to be updated
     * in update query.
     *
     * @return List<String> of field names of this class
     */
    public static List<String> getUpdateEligibleFieldNames() {
        List<String> fieldList = new ArrayList<>();
        fieldList.add("price");
        fieldList.add("color");
        fieldList.add("productName");
        fieldList.add("inventory");
        return fieldList;
    }

    public ProductDTO(String productId, float price, String color, String productName, String inventory, PropertiesDTO properties) {
        this();
        this.productId = productId;
        this.price = price;
        this.color = color;
        this.productName = productName;
        this.inventory = inventory;
        this.properties = properties;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public PropertiesDTO getProperties() {
        return properties;
    }

    public void setProperties(PropertiesDTO properties) {
        this.properties = properties;
    }
}
