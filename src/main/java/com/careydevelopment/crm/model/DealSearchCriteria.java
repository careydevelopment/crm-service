package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Sort.Direction;

public class DealSearchCriteria {

    private String contactId;
    private Direction orderType = Direction.DESC;
    
    
    public String getContactId() {
        return contactId;
    }
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    
    public Direction getOrderType() {
        return orderType;
    }
    public void setOrderType(Direction orderType) {
        this.orderType = orderType;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
