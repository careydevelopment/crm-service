package com.careydevelopment.crm.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{@environment.getProperty('mongo.deal-stage.collection')}")
public class DealStage {

    @Id
    private String id;
    
    @NotNull(message = "Please include a sales type")
    private SalesType salesType;
    
    @Min(value = 0, message = "Index must be at least 0")
    private Integer index;

    @NotBlank(message = "Please provide a stage name")
    @Size(max = 20, message = "Stage name must be between 1 and 20 characters")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SalesType getSalesType() {
        return salesType;
    }

    public void setSalesType(SalesType salesType) {
        this.salesType = salesType;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
