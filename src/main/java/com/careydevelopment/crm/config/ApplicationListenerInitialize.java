package com.careydevelopment.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.repository.ActivityOutcomeRepository;
import com.careydevelopment.crm.repository.ActivityRepository;
import com.careydevelopment.crm.repository.ActivityTypeRepository;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.repository.DealStageRepository;
import com.careydevelopment.crm.service.ContactService;

@Component
public class ApplicationListenerInitialize implements ApplicationListener<ApplicationReadyEvent>  {
    
    
    private String data = "";

    @Autowired
    private DealStageRepository dealStageRepo;
    
    @Autowired
    private DealRepository dealRepo;
    
    @Autowired
    private ActivityOutcomeRepository activityOutcomeRepo;
    
    @Autowired
    private ActivityTypeRepository activityTypeRepo;
    
    @Autowired
    private ActivityRepository activityRepo;
    
    @Autowired
    private ContactService contactService;
    
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String bearerToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaWx0b24iLCJhdWQiOiJjYXJleWRldmVsb3BtZW50LWVjb3N5c3RlbS11c2VycyIsImlkIjoiNjAxNDA4MWUyMjFlMWI1MzRhOGFhNDMyIiwiZXhwIjoxNjEyMzEwMTI3LCJpYXQiOjE2MTIyMjM3MjcsImF1dGhvcml0aWVzIjpbIkNBUkVZREVWRUxPUE1FTlRfQ1JNX1VTRVIiXX0.BCxBBPo4yN8zb_acgjJ8FMU-5X8VzRbIQiomkr2uIf48j8uRARHxWom40QGaKGCs2CzH72qMS92mxUdHvByACQ";
        String contactId = "6014199147692f4194ff9d";
        
        try {
            Contact contact = contactService.fetchContact(bearerToken, contactId);
            System.err.println(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        

}