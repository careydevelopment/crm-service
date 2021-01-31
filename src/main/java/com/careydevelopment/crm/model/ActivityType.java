package com.careydevelopment.crm.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ActivityType extends ActivityTypeLightweight {

    private Boolean requiresOutcome = true;
    private Boolean usesLocation = false;
    
    private String icon;
    private List<ActivityOutcome> possibleOutcomes = new ArrayList<>();
    
    public Boolean getRequiresOutcome() {
        return requiresOutcome;
    }
    public void setRequiresOutcome(Boolean requiresOutcome) {
        this.requiresOutcome = requiresOutcome;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public List<ActivityOutcome> getPossibleOutcomes() {
        return possibleOutcomes;
    }
    public void setPossibleOutcomes(List<ActivityOutcome> possibleOutcomes) {
        this.possibleOutcomes = possibleOutcomes;
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
