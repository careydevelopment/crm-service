package com.careydevelopment.crm.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.Deal;
import com.careydevelopment.crm.model.DealSearchCriteria;
import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.DealService;
import com.careydevelopment.crm.service.ServiceException;
import com.careydevelopment.crm.util.DealValidator;

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
    
    @Autowired
    private DealValidator dealValidator;
    
    
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
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchDealById(@PathVariable String id) {
        LOG.debug("Fetching deal " + id);
       
        Optional<Deal> dealOpt = dealRepository.findById(id);
        if (dealOpt.isPresent()) {
            Deal deal = dealOpt.get();
            
            Contact contact = deal.getContact();
            LOG.debug("Contact is " + contact);
            String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!contact.getSalesOwner().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            
            LOG.debug("Returning deal " + deal);
            
            return ResponseEntity.status(HttpStatus.OK).body(deal);   
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find deal " + id);
        }        
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable String id, @Valid @RequestBody Deal deal, HttpServletRequest request) {
        LOG.debug("Updating deal " + id + " with data " + deal);
       
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (id == null || id.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID is required");
        } else if (!id.equals(deal.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID in URL and body don't match");
        }

        ErrorResponse errorResponse = dealValidator.validateDeal(deal, bearerToken);
        if (errorResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } 
        
        Deal newDeal = dealRepository.save(deal); 
        
        return ResponseEntity.ok(newDeal);
    }
    
    
    @PostMapping("")
    public ResponseEntity<?> createDeal(@Valid @RequestBody Deal deal, HttpServletRequest request) {
        LOG.debug("I'm saving " + deal);
        
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        ErrorResponse errorResponse = dealValidator.validateDeal(deal, bearerToken);
        if (errorResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        Deal returnedDeal = dealRepository.save(deal);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedDeal);
    }
}
