 package com.careydevelopment.crm.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import us.careydevevlopment.model.activities.BaseActivityType;

@Document(collection = "#{@environment.getProperty('mongo.activity-type.collection')}")
public class ActivityType extends BaseActivityType<ActivityOutcome> {

    @Id
    private String id;

    protected String icon;


    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getIcon() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ActivityOutcome> getPossibleOutcomes() {
        return this.possibleOutcomes;
    }
    
    public void setPossibleOutcomes(List<ActivityOutcome> possibleOutcomes) {
        this.possibleOutcomes = possibleOutcomes;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
        ActivityOutcome other = (ActivityOutcome) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }
}
