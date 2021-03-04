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
    private ContactValidator contactValidator;
    
    @Autowired
    private ActivityTypeRepository activityTypeRepository;
    
    @Autowired
    private ActivityOutcomeRepository activityOutcomeRepository;
    
    
    public ErrorResponse validateActivity(Activity activity, String bearerToken) {
        ErrorResponse errorResponse = new ErrorResponse();        

        activity = (Activity)SpaceUtil.trimReflective(activity);
        
        contactValidator.validateContact(activity.getContact(), errorResponse, bearerToken);
        
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
            ErrorHandler.addError(errorResponse, "Date required", "startDate", "dateRequired");
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
                    ErrorHandler.addError(errorResponse, "Invalid activity type ID: " + activityType.getId(), "type", "invalidActivityTypeId");
                }
            } else {
                ErrorHandler.addError(errorResponse, "Missing activity type ID", "type", "missingActivityTypeId");                
            }
        } else {
            ErrorHandler.addError(errorResponse, "Missing activity type", "type", "missingActivityType");
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
                    ErrorHandler.addError(errorResponse, "Invalid activity outcome ID: " + activityOutcome.getId(), "outcome", "invalidActivityOutcomeId");
                }
            } else {
                activityOutcome = null;                
            }
        } 
    }
}
