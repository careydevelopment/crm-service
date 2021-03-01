package com.careydevelopment.crm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.Deal;
import com.careydevelopment.crm.model.DealSearchCriteria;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.DealService;
import com.careydevelopment.crm.service.ServiceException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/deals")
public class DealController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DealController.class);
        

    @Autowired
    private DealRepository dealRepository;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private DealService dealService;
    
    
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String contactId, HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            Contact contact = contactService.fetchContact(bearerToken, contactId);
            
            if (contact != null) {
                DealSearchCriteria searchCriteria = new DealSearchCriteria();
                searchCriteria.setContactId(contactId);
                
                LOG.debug("Search criteria is " + searchCriteria);
                
                List<Deal> deals = dealService.search(searchCriteria);
                
                LOG.debug("Returning deals " + deals);
                return ResponseEntity.ok(deals);                
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact with ID:" + contactId);
            }
        } catch (ServiceException se) {
            return ResponseEntity.status(HttpStatus.valueOf(se.getStatusCode())).body(se.getMessage());
        }
    }
}
