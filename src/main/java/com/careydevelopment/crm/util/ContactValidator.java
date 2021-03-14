package com.careydevelopment.crm.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Account;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.model.SalesOwner;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.ServiceException;
import com.careydevelopment.crm.service.UserService;

@Component
public class ContactValidator {
    
    private static final Logger LOG = LoggerFactory.getLogger(ContactValidator.class);


    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserService userService;
    
    
    public void validateContact(Contact contact, ErrorResponse errorResponse, String bearerToken) {
        if (contact != null) {
            if (!StringUtils.isBlank(contact.getId())) {
                try {
                    Contact fetchedContact = contactService.fetchContact(bearerToken, contact.getId());
                    
                    //rather than validate all this data, just set it from the source of truth
                    contact.setFirstName(fetchedContact.getFirstName());
                    contact.setLastName(contact.getLastName());
                    
                    Account account = new Account();
                    account.setId(fetchedContact.getAccount().getId());
                    account.setName(fetchedContact.getAccount().getName());
                    contact.setAccount(account);
                    
                    SalesOwner salesOwner = userService.fetchUser(bearerToken);
                    contact.setSalesOwner(salesOwner);
                } catch (ServiceException se) {
                    LOG.error("Problem fetching contact!", se);
                    
                    if (se.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                        ErrorHandler.addError(errorResponse, "Invalid contact ID: " + contact.getId(), "contact", "invalidContactId");
                    } else {
                        ErrorHandler.addError(errorResponse, "Problem retrieving contact " + contact.getId(), "contact", "internalError");
                    }
                }
            } else {
                ErrorHandler.addError(errorResponse, "Missing contact ID", "contact", "missingContactId");
            }
        } else {
            ErrorHandler.addError(errorResponse, "Missing contact", "contact", "missingContact");
        }
    }

}
