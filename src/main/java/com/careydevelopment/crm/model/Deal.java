package com.careydevelopment.crm.model;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{@environment.getProperty('mongo.deal.collection')}")
public class Deal {

    @Id
    private String id;
    
    @NotBlank(message = "Please provide a name for this deal")
    @Size(max = 50, message = "Deal name must be between 1 and 50 characters")
    private String name;

    @Size(max = 1024, message = "Deal description cannot exceed 1024 characters")
    private String description;
    
    @NotNull(message = "Please associate this deal with a product")
    private Product product;
    
    @NotNull(message = "Please associate this deal with a contact")
    private Contact contact;
    
    private Integer units;
    
    private DealStage stage;
    
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



    public Product getProduct() {
        return product;
    }



    public void setProduct(Product product) {
        this.product = product;
    }



    public Contact getContact() {
        return contact;
    }



    public void setContact(Contact contact) {
        this.contact = contact;
    }

    
    

    public Integer getUnits() {
        return units;
    }



    public void setUnits(Integer units) {
        this.units = units;
    }

    
    

    public DealStage getStage() {
        return stage;
    }



    public void setStage(DealStage stage) {
        this.stage = stage;
    }



    public Integer getValue(CurrencyType currencyType) {
        Integer value = 0;
        
        if (product.getProductType().equals(ProductType.SERVICE)) {
            value = getServiceValue(currencyType);
        } else {
            value = getProductValue(currencyType);
        }
        
        return value;
    }
    
    
    private Integer getProductValue(CurrencyType currencyType) {
        Integer value = 0;
        
        Price price = getPrice(currencyType);
        if (price != null) {
            value = price.getAmount();
        }
        
        return value;
    }
    
    
    private Integer getServiceValue(CurrencyType currencyType) {
        Integer value = 0;
        
        Price price = getPrice(currencyType);
        if (price != null) {
            if (PriceType.FLAT_RATE.equals(price.getPriceType())) {
                value = price.getAmount();
            } else if (PriceType.PER_UNIT.equals(price.getPriceType())) {
                value = units * price.getAmount();
            }
        }
        
        return value;
    }

    
    private Price getPrice(CurrencyType currencyType) {
        Price price = null;

        if (currencyType != null) {
            Optional<Price> matchedPrice = product
                                            .getPrices()
                                            .stream()
                                            .filter(p -> currencyType.equals(p.getCurrencyType()))
                                            .findFirst();
            
            if (matchedPrice.isPresent()) price = matchedPrice.get();

        }
        
        return price;
    }
    
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
