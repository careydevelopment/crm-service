package com.careydevelopment.crm.model;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import us.careydevevlopment.model.activities.ActivityOwner;
import us.careydevevlopment.model.activities.BaseActivity;


@Document(collection = "#{@environment.getProperty('mongo.activity.collection')}")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity extends BaseActivity<ActivityOutcome, ActivityType> {

    @Id
    private String id;

    @NotNull(message = "Please associate a contact with this activity")
    private Contact contact;
        
    private DealLightweight deal;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
    public DealLightweight getDeal() {
        return deal;
    }

    public void setDeal(DealLightweight deal) {
        this.deal = deal;
    }

    public ActivityType getType() {
        return this.type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    @Override
    public ActivityOutcome getOutcome() {
        return outcome;
    }

    @Override
    public void setOutcome(ActivityOutcome outcome) {
        this.outcome = outcome;
    }
    
    @Override
    public SalesOwner getOwner() {
        return contact.getSalesOwner();
    }
    
    @Override
    public void setOwner(ActivityOwner owner) {
        // TODO do nothing for now
        //owner details are stored with the contact
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
        Activity other = (Activity) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

}
