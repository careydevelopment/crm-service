package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Sort.Direction;

public class DealSearchCriteria {

    private String contactId;
    private String salesOwnerId;
    private Direction orderType = Direction.DESC;
    private String orderBy = "expectedClosureDate";
    private Integer maxResults = 20;
    private Long minDate;
    private Long maxDate;
    
    
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
    
    public String getSalesOwnerId() {
        return salesOwnerId;
    }
    public void setSalesOwnerId(String salesOwnerId) {
        this.salesOwnerId = salesOwnerId;
    }
    
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    public Integer getMaxResults() {
        return maxResults;
    }
    
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
    
    public Long getMinDate() {
        return minDate;
    }
    
    public void setMinDate(Long minDate) {
        this.minDate = minDate;
    }
    
    public Long getMaxDate() {
        return maxDate;
    }
    
    public void setMaxDate(Long maxDate) {
        this.maxDate = maxDate;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
