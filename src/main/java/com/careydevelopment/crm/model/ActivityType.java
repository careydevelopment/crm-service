 package com.careydevelopment.crm.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ActivityType extends ActivityTypeLightweight {

    private Boolean requiresOutcome = true;
    private Boolean usesLocation = false;
    private Boolean usesEndDate = false;
    
    private List<ActivityOutcome> possibleOutcomes = new ArrayList<>();
    
    public Boolean getRequiresOutcome() {
        return requiresOutcome;
    }
    public void setRequiresOutcome(Boolean requiresOutcome) {
        this.requiresOutcome = requiresOutcome;
    }
    
    public List<ActivityOutcome> getPossibleOutcomes() {
        return possibleOutcomes;
    }
    public void setPossibleOutcomes(List<ActivityOutcome> possibleOutcomes) {
        this.possibleOutcomes = possibleOutcomes;
    }
    
    public Boolean getUsesEndDate() {
        return usesEndDate;
    }
    public void setUsesEndDate(Boolean usesEndDate) {
        this.usesEndDate = usesEndDate;
    }
    
    public Boolean getUsesLocation() {
        return usesLocation;
    }
    public void setUsesLocation(Boolean usesLocation) {
        this.usesLocation = usesLocation;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
