package com.careydevelopment.crm.util;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Account;
import com.careydevelopment.crm.model.Activity;
import com.careydevelopment.crm.model.ActivityOutcome;
import com.careydevelopment.crm.model.ActivityType;
import com.careydevelopment.crm.model.ActivityTypeLightweight;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.model.SalesOwner;
import com.careydevelopment.crm.model.ValidationError;
import com.careydevelopment.crm.repository.ActivityOutcomeRepository;
import com.careydevelopment.crm.repository.ActivityTypeRepository;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.ServiceException;
import com.careydevelopment.crm.service.UserService;

@Component
public class ActivityValidator {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityValidator.class);
    
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ActivityTypeRepository activityTypeRepository;
    
    @Autowired
    private ActivityOutcomeRepository activityOutcomeRepository;
    
    @Autowired
    private UserService userService;
    
    public ErrorResponse validateActivity(Activity activity, String bearerToken) {
        ErrorResponse errorResponse = new ErrorResponse();        

        activity = (Activity)SpaceUtil.trimReflective(activity);
        
        validateContact(activity.getContact(), errorResponse, bearerToken);
        validateActivityType(activity.getType(), errorResponse);        
        validateActivityOutcome(activity.getOutcome(), errorResponse);
        
        validateDates(activity, errorResponse);
        
        if (errorResponse.getErrors().size() == 0) errorResponse = null;
        return errorResponse;
    }

    
    private void validateDates(Activity activity, ErrorResponse errorResponse) {
        validateStartDate(activity.getStartDate(), errorResponse);
    }
    
    
    private void validateStartDate(Long startDate, ErrorResponse errorResponse) {
        if (startDate == null) {
            addError(errorResponse, "Date required", "startDate", "dateRequired");
        }
    }
    
        
    private void validateContact(Contact contact, ErrorResponse errorResponse, String bearerToken) {
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
                        addError(errorResponse, "Invalid contact ID: " + contact.getId(), "contact", "invalidContactId");
                    } else {
                        addError(errorResponse, "Problem retrieving contact " + contact.getId(), "contact", "internalError");
                    }
                }
            } else {
                addError(errorResponse, "Missing contact ID", "contact", "missingContactId");
            }
        } else {
            addError(errorResponse, "Missing contact", "contact", "missingContact");
        }
    }

    
    private void validateActivityType(ActivityTypeLightweight activityType, ErrorResponse errorResponse) {
        if (activityType != null) {
            if (!StringUtils.isBlank(activityType.getId())) {
                Optional<ActivityType> fetchedActivityTypeOpt = activityTypeRepository.findById(activityType.getId());
                
                if (fetchedActivityTypeOpt.isPresent()) {
                    ActivityType fetchedActivityType = fetchedActivityTypeOpt.get();
                    
                    activityType.setIcon(fetchedActivityType.getIcon());
                    activityType.setName(fetchedActivityType.getName());
                } else {
                    addError(errorResponse, "Invalid activity type ID: " + activityType.getId(), "type", "invalidActivityTypeId");
                }
            } else {
                addError(errorResponse, "Missing activity type ID", "type", "missingActivityTypeId");                
            }
        } else {
            addError(errorResponse, "Missing activity type", "type", "missingActivityType");
        }
    }
    
    
    private void validateActivityOutcome(ActivityOutcome activityOutcome, ErrorResponse errorResponse) {
        if (activityOutcome != null) {
            if (!StringUtils.isBlank(activityOutcome.getId())) {
                Optional<ActivityOutcome> fetchedActivityOutcomeOpt = activityOutcomeRepository.findById(activityOutcome.getId());
                
                if (fetchedActivityOutcomeOpt.isPresent()) {
                    ActivityOutcome fetchedActivityOutcome = fetchedActivityOutcomeOpt.get();

                    activityOutcome.setName(fetchedActivityOutcome.getName());
                } else {
                    addError(errorResponse, "Invalid activity outcome ID: " + activityOutcome.getId(), "outcome", "invalidActivityOutcomeId");
                }
            } else {
                activityOutcome = null;                
            }
        } 
    }
    
    
    private void addError(ErrorResponse errorResponse, String errorMessage, String field, String code) {        
        ValidationError validationError = new ValidationError();
        validationError.setCode(code);
        validationError.setDefaultMessage(errorMessage);
        validationError.setField(field);
        
        errorResponse.getErrors().add(validationError);
    }
}
