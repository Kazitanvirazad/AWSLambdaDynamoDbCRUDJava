package com.serverless.dto;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesDTO implements Serializable {
    private static final long serialVersionUID = -5961307757548219661L;
    private String category;
    private String connectionType;

    /**
     * Creates AttributeValue Map from this PropertiesDTO object
     *
     * @return Map<String, AttributeValue> from this PropertiesDTO object
     */
    public Map<String, AttributeValue> createAttributeValueMap() {
        Map<String, AttributeValue> propertiesItem = new HashMap<>();
        if (this.category != null)
            propertiesItem.put("category", AttributeValue.builder().s(this.category).build());
        if (this.connectionType != null)
            propertiesItem.put("connectionType", AttributeValue.builder().s(this.connectionType).build());
        return propertiesItem;
    }

    /**
     * Creates PropertiesDTO object from attributeValueMap
     *
     * @param attributeValueMap
     * @return PropertiesDTO
     */
    public static PropertiesDTO createPropertiesDTOFromAttributeMap(Map<String, AttributeValue> attributeValueMap) {
        return new PropertiesDTO(attributeValueMap.get("category").s(),
                attributeValueMap.get("connectionType").s());
    }

    /**
     * Returns list of field names which are eligible to be updated
     * in update query.
     *
     * @return List<String> of field names of this class
     */
    public static List<String> getUpdateEligibleFieldNames() {
        List<String> fieldList = new ArrayList<>();
        fieldList.add("category");
        fieldList.add("connectionType");
        return fieldList;
    }

    public PropertiesDTO() {
        super();
    }

    public PropertiesDTO(String category, String connectionType) {
        this();
        this.category = category;
        this.connectionType = connectionType;
    }

    public String getCategory() {
        return category;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
