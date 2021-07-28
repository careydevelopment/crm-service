package com.careydevelopment.crm.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.Activity;
import com.careydevelopment.crm.model.ActivitySearchCriteria;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.model.SalesOwner;
import com.careydevelopment.crm.repository.ActivityRepository;
import com.careydevelopment.crm.service.ActivityService;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.ServiceException;
import com.careydevelopment.crm.service.UserService;
import com.careydevelopment.crm.util.ActivityValidator;
import com.careydevelopment.crm.util.SecurityUtil;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);
        

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ActivityValidator activityValidator;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private SecurityUtil securityUtil;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String contactId, @RequestParam(required = false) Long minDate,
            @RequestParam(required = false) String orderBy, @RequestParam(required = false) String orderType, 
            @RequestParam(required = false) String dealId, @RequestParam(required = false) String salesOwnerId, 
            @RequestParam(required = false) String status, @RequestHeader("Authorization") String bearerToken) {
        
        try {
            if (!StringUtils.isBlank(contactId)) {
                Contact contact = contactService.fetchContact(bearerToken, contactId);
                
                if (contact == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact with ID:" + contactId);
                else if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            
            if (!StringUtils.isBlank(salesOwnerId)) {
                SalesOwner salesOwner = userService.fetchUser(bearerToken);
                
                if (salesOwner == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sales owner with ID:" + salesOwnerId);
                else if (!securityUtil.isAuthorizedToAccessUser(salesOwner)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            
            ActivitySearchCriteria searchCriteria = new ActivitySearchCriteria();
            searchCriteria.setContactId(contactId);
            searchCriteria.setMinDate(minDate);
            searchCriteria.setOrderBy(orderBy);
            searchCriteria.setOrderType("ASC".equals(orderType) ? Direction.ASC : Direction.DESC);
            searchCriteria.setDealId(dealId);
            searchCriteria.setSalesOwnerId(salesOwnerId);
            searchCriteria.setStatus(status);
            
            LOG.debug("Search criteria is " + searchCriteria);
            
            List<Activity> activities = activityService.search(searchCriteria);
            LOG.debug("Returning activities " + activities);
            
            return ResponseEntity.ok(activities);                
        } catch (ServiceException se) {
            return ResponseEntity.status(HttpStatus.valueOf(se.getStatusCode())).body(se.getMessage());
        }
    }
    
    
    @PostMapping("")
    public ResponseEntity<?> createActivity(@Valid @RequestBody Activity activity, HttpServletRequest request) {
        LOG.debug("I'm saving " + activity);

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (activity.getContact() != null) {
            Contact contact = contactService.fetchContact(bearerToken, activity.getContact().getId());
            
            if (contact == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact with ID:" + activity.getContact().getId());
            else if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
                
        ErrorResponse errorResponse = activityValidator.validateActivity(activity, bearerToken);
        if (errorResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        Activity returnedActivity = activityRepository.save(activity);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedActivity);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchActivityById(@PathVariable String id) {
        LOG.debug("Fetching activity " + id);
       
        Optional<Activity> activityOpt = activityRepository.findById(id);
        if (activityOpt.isPresent()) {
            Activity activity = activityOpt.get();           
            Contact contact = activity.getContact();
 
            if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            
            LOG.debug("Returning activity " + activity);
            
            return ResponseEntity.status(HttpStatus.OK).body(activity);   
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find activity " + id);
        }        
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable("id") String id, @Valid @RequestBody Activity activity, 
            HttpServletRequest request) {
        LOG.debug("Updating activity id: " + id + " with data " + activity);
                
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        Optional<Activity> activityOpt = activityRepository.findById(id);
        if (activityOpt.isPresent()) {
            Activity existingActivity = activityOpt.get();           
            Contact contact = existingActivity.getContact();
 
            if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
        }
        
        if (id == null || id.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID is required");
        } else if (!id.equals(activity.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID in URL and body don't match");
        }

        ErrorResponse errorResponse = activityValidator.validateActivity(activity, bearerToken);
        if (errorResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } 
        
        Activity newActivity = activityRepository.save(activity); 
        
        return ResponseEntity.ok(newActivity);
    }
}
