package com.careydevelopment.crm.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{@environment.getProperty('mongo.activity-type.collection')}")
public class ActivityTypeLightweight {

    @Id
    private String id;

    @NotBlank(message = "Please provide a name for this activity type")
    @Size(max = 20, message = "Activity type name must be between 1 and 20 characters")
    private String name;
    
    
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


    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }    
}
