package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Contact {

    private String id;
    private String firstName;
    private String lastName;
    private Account account;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
