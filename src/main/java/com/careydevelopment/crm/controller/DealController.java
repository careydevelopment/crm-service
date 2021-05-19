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
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.careydevelopment.crm.model.SalesOwner;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.DealService;
import com.careydevelopment.crm.service.ServiceException;
import com.careydevelopment.crm.service.UserService;
import com.careydevelopment.crm.util.DealValidator;
import com.careydevelopment.crm.util.SecurityUtil;

@RestController
@RequestMapping("/deals")
public class DealController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DealController.class);
        

    @Autowired
    private DealRepository dealRepository;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DealService dealService;
    
    @Autowired
    private DealValidator dealValidator;
    
    @Autowired
    private SecurityUtil securityUtil;
    
    
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String contactId, 
            @RequestParam(required = false) String salesOwnerId, @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) Direction orderType, @RequestParam(required = false) Integer maxResults,
            @RequestParam(required = false) Long minDate, @RequestParam(required = false) Long maxDate,
            HttpServletRequest request) {
        
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        LOG.debug("Contact ID is " + contactId);
        
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
                
                if (salesOwner == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Could not validate credentials");
                } else if (!salesOwner.getId().equals(salesOwnerId)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to view that user's deals");
                }
            }

            DealSearchCriteria searchCriteria = new DealSearchCriteria();
            searchCriteria.setContactId(contactId);
            searchCriteria.setSalesOwnerId(salesOwnerId);
            searchCriteria.setMaxDate(maxDate);
            searchCriteria.setMinDate(minDate);
            if (!StringUtils.isBlank(orderBy)) searchCriteria.setOrderBy(orderBy);
            if (orderType != null) searchCriteria.setOrderType(orderType);
            if (maxResults != null) searchCriteria.setMaxResults(maxResults);
            
            LOG.debug("Search criteria is " + searchCriteria);
            
            List<Deal> deals = dealService.search(searchCriteria);
            
            LOG.debug("Returning deals " + deals);
            return ResponseEntity.ok(deals);                
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

            if (!securityUtil.isAuthorizedToAccessContact(contact)) {
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

        Optional<Deal> dealOpt = dealRepository.findById(id);
        if (dealOpt.isPresent()) {
            Deal existingDeal = dealOpt.get();
            
            Contact contact = existingDeal.getContact();

            if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
        }
        
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
        
        if (deal.getContact() != null) {
            Contact contact = contactService.fetchContact(bearerToken, deal.getContact().getId());
            
            if (contact == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact with ID:" + deal.getContact().getId());
            else if (!securityUtil.isAuthorizedToAccessContact(contact)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        
        ErrorResponse errorResponse = dealValidator.validateDeal(deal, bearerToken);
        if (errorResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        Deal returnedDeal = dealRepository.save(deal);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedDeal);
    }
}
