package com.careydevelopment.crm.model;

import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Deal extends DealLightweight {

    @NotNull(message = "Please associate this deal with a product")
    private Product product;
    
    @NotNull(message = "Please associate this deal with a contact")
    private Contact contact;
    
    @Min(value=1, message="Units must be at least 1")
    private Integer units;
    
    private DealStage stage;

    private Long expectedClosureDate;
        

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



    public Long getExpectedClosureDate() {
        return expectedClosureDate;
    }



    public void setExpectedClosureDate(Long expectedClosureDate) {
        this.expectedClosureDate = expectedClosureDate;
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
