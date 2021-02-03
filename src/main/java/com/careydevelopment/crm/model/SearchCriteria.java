package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Sort.Direction;

public class SearchCriteria {

    private String contactId;
    private Long minDate;
    private String orderBy = "date";
    private Direction orderType = Direction.DESC;
    
    
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
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
