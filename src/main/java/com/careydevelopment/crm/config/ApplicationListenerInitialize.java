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
        
        
    ActivityType type1 = new ActivityType();
    type1.setIcon("phone");
    type1.setName("Phone Call");
    type1.setPossibleOutcomes(List.of(outcome(outcomes, "Not Interested"), outcome(outcomes, "Did Not Respond"),
            outcome(outcomes, "Interested"), outcome(outcomes, "Appointment Scheduled"), outcome(outcomes, "Demo Scheduled"), outcome(outcomes, "Left Message")));
    type1.setRequiresOutcome(true);
    type1.setUsesLocation(false);
    type1.setUsesEndDate(false);
    type1.setActivityTypeCreator(ActivityTypeCreator.USER);
    type1.setUsesStatus(false);
    
    ActivityType type2 = new ActivityType();
    type2.setIcon("chat");
    type2.setName("Chat");
    type2.setPossibleOutcomes(List.of(outcome(outcomes, "Not Interested"), outcome(outcomes, "Did Not Respond"),
            outcome(outcomes, "Interested"), outcome(outcomes, "Appointment Scheduled"), outcome(outcomes, "Demo Scheduled")));
    type2.setRequiresOutcome(true);
    type2.setUsesLocation(false);
    type2.setUsesEndDate(false);
    type2.setActivityTypeCreator(ActivityTypeCreator.USER);
    type2.setUsesStatus(false);
    
    ActivityType type3 = new ActivityType();
    type3.setIcon("calendar_today");
    type3.setName("Appointment");
    type3.setPossibleOutcomes(List.of(outcome(outcomes, "Not Interested"), outcome(outcomes, "Did Not Respond"),
            outcome(outcomes, "Interested"), outcome(outcomes, "Appointment Scheduled"), outcome(outcomes, "Demo Scheduled")));
    type3.setRequiresOutcome(true);
    type3.setUsesLocation(true);
    type3.setUsesEndDate(true);
    type3.setActivityTypeCreator(ActivityTypeCreator.USER);
    type3.setUsesStatus(false);
    
    ActivityType type4 = new ActivityType();
    type4.setIcon("textsms");
    type4.setName("Text Message");
    type4.setPossibleOutcomes(List.of(outcome(outcomes, "Not Interested"), outcome(outcomes, "Did Not Respond"),
            outcome(outcomes, "Interested"), outcome(outcomes, "Appointment Scheduled"), outcome(outcomes, "Demo Scheduled")));
    type4.setRequiresOutcome(true);
    type4.setUsesLocation(false);
    type4.setUsesEndDate(false);
    type4.setActivityTypeCreator(ActivityTypeCreator.USER);
    type4.setUsesStatus(false);
    
    ActivityType type5 = new ActivityType();
    type5.setIcon("list_alt");
    type5.setName("Web Form Completion");
    type5.setRequiresOutcome(false);
    type5.setUsesLocation(false);
    type5.setUsesEndDate(false);
    type5.setActivityTypeCreator(ActivityTypeCreator.SYSTEM);
    type5.setUsesStatus(false);
    
    ActivityType type6 = new ActivityType();
    type6.setIcon("web");
    type6.setName("Web Page Visitied");
    type6.setRequiresOutcome(false);
    type6.setUsesLocation(false);
    type6.setUsesEndDate(false);
    type6.setActivityTypeCreator(ActivityTypeCreator.SYSTEM);
    type6.setUsesStatus(false);
    
    ActivityType type7 = new ActivityType();
    type7.setIcon("money");
    type7.setName("Purchase");
    type7.setRequiresOutcome(false);
    type7.setUsesLocation(false);
    type7.setUsesEndDate(false);
    type7.setActivityTypeCreator(ActivityTypeCreator.SYSTEM);
    type7.setUsesStatus(false);
    
    ActivityType type8 = new ActivityType();
    type8.setIcon("task");
    type8.setName("Task");
    type8.setRequiresOutcome(false);
    type8.setUsesLocation(true);
    type8.setUsesEndDate(false);
    type8.setActivityTypeCreator(ActivityTypeCreator.USER);
    type8.setUsesStatus(true);
    
//    activityTypeRepo.save(type1);
//    activityTypeRepo.save(type2);
//    activityTypeRepo.save(type3);
//    activityTypeRepo.save(type4);
//    activityTypeRepo.save(type5);
//    activityTypeRepo.save(type6);
//    activityTypeRepo.save(type7);
//    activityTypeRepo.save(type8);

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