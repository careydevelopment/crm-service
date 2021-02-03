package com.careydevelopment.crm.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{@environment.getProperty('mongo.activity.collection')}")
public class Activity {

    @Id
    private String id;

    @NotNull(message = "Please include an activity type")
    private ActivityTypeLightweight type;
    
    @NotBlank(message = "Please provide a title for this activity")
    @Size(max = 50, message = "Activity name must be between 1 and 50 characters")
    private String title;
    
    private ActivityOutcome outcome;
    
    @Size(max = 512, message = "Activity notes must not exceed 512 characters")
    private String notes;

    @Size(max = 50, message = "Activity location must not exceed 50 characters")
    private String location;
    
    @NotNull(message = "Please include an activity start date")
    private Long startDate;

    @NotNull(message = "Please include an activity end date")
    private Long endDate;
    
    @NotNull(message = "Please associate a contact with this activity")
    private Contact contact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActivityTypeLightweight getType() {
        return type;
    }

    public void setType(ActivityTypeLightweight type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ActivityOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(ActivityOutcome outcome) {
        this.outcome = outcome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
