package com.careydevelopment.crm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.Activity;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.SearchCriteria;
import com.careydevelopment.crm.service.ActivityService;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.ServiceException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/activities")
public class ActivityController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);
        

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ContactService contactService;
    
    
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String contactId, @RequestParam(required = false) Long minDate,
            @RequestParam(required = false) String orderBy, @RequestParam(required = false) String orderType, HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            Contact contact = contactService.fetchContact(bearerToken, contactId);
            
            if (contact != null) {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.setContactId(contactId);
                searchCriteria.setMinDate(minDate);
                searchCriteria.setOrderBy(orderBy);
                searchCriteria.setOrderType("ASC".equals(orderType) ? Direction.ASC : Direction.DESC);
                
                List<Activity> activities = activityService.search(searchCriteria);
                LOG.debug("Returning activities " + activities);
                return ResponseEntity.ok(activities);                
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact with ID:" + contactId);
            }
        } catch (ServiceException se) {
            return ResponseEntity.status(HttpStatus.valueOf(se.getStatusCode())).body(se.getMessage());
        }
    }
}
