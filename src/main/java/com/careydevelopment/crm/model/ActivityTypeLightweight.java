package com.careydevelopment.crm.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    
    private String icon;

    @NotNull(message = "Please include an activity type creator")
    private ActivityTypeCreator activityTypeCreator;
    
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

    
    public String getIcon() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    
    
    public ActivityTypeCreator getActivityTypeCreator() {
        return activityTypeCreator;
    }


    public void setActivityTypeCreator(ActivityTypeCreator activityTypeCreator) {
        this.activityTypeCreator = activityTypeCreator;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActivityTypeLightweight other = (ActivityTypeLightweight) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }    
}
