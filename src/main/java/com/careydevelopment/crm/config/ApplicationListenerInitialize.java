package com.careydevelopment.crm.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.ActivityOutcome;
import com.careydevelopment.crm.model.ActivityType;
import com.careydevelopment.crm.repository.ActivityOutcomeRepository;
import com.careydevelopment.crm.repository.ActivityRepository;
import com.careydevelopment.crm.repository.ActivityTypeRepository;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.repository.DealStageRepository;
import com.careydevelopment.crm.repository.SalesTypeRepository;
import com.careydevelopment.crm.service.ActivityService;
import com.careydevelopment.crm.service.ContactService;
import com.careydevelopment.crm.service.DealService;
import com.careydevelopment.crm.service.UserService;

import us.careydevevlopment.model.activities.ActivityTypeCreator;

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
    
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DealService dealService;

    @Autowired
    private SalesTypeRepository salesTypeRepo;
    
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<ActivityOutcome> outcomes = activityOutcomeRepo.findAll();




    }
    
    
    private ActivityOutcome outcome(List<ActivityOutcome> outcomes, String match) {
        ActivityOutcome outcome = new ActivityOutcome();
        
        for (ActivityOutcome oc : outcomes) {
            if (oc.getName().equals(match)) {
                outcome = oc;
                break;
            }
        }
        
        return outcome;
    }
}