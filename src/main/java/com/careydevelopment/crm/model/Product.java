package com.careydevelopment.crm.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class Product {

    private String id;    
    private String name;
    private String description;
    private LineOfBusiness lineOfBusiness;
    private ProductType productType;
    private List<Price> prices = new ArrayList<>();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
    
    public LineOfBusiness getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public void addPrice(Price price) {
        prices.add(price);
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
