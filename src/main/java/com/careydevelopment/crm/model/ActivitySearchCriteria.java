package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Sort.Direction;

public class ActivitySearchCriteria {

    private String contactId;
    private Long minDate;
    private String orderBy = "startDate";
    private Direction orderType = Direction.DESC;
    private String dealId;
    
    
    public String getContactId() {
        return contactId;
    }
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    public Long getMinDate() {
        return minDate;
    }
    public void setMinDate(Long minDate) {
        this.minDate = minDate;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    public Direction getOrderType() {
        return orderType;
    }
    public void setOrderType(Direction orderType) {
        this.orderType = orderType;
    }
    
    public String getDealId() {
        return dealId;
    }
    public void setDealId(String dealId) {
        this.dealId = dealId;
    }
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
