package com.careydevelopment.crm.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Account;
import com.careydevelopment.crm.model.Activity;
import com.careydevelopment.crm.model.ActivityOutcome;
import com.careydevelopment.crm.model.ActivityType;
import com.careydevelopment.crm.model.ActivityTypeLightweight;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.DealStage;
import com.careydevelopment.crm.repository.ActivityOutcomeRepository;
import com.careydevelopment.crm.repository.ActivityRepository;
import com.careydevelopment.crm.repository.ActivityTypeRepository;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.repository.DealStageRepository;

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
    
    
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
    private DealStage dealStage(List<DealStage> stages, Integer index) {
        DealStage stage = null;
        
        Optional<DealStage> stageOpt = stages.stream().filter(st -> st.getIndex().equals(index)).findFirst();
        if (stageOpt.isPresent()) stage = stageOpt.get();
        
        return stage;
    }
    
    
    private ActivityOutcome outcome(List<ActivityOutcome> outcomes, String name) {
        ActivityOutcome outcome = null;
        
        Optional<ActivityOutcome> outcomeOpt = outcomes.stream().filter(oc -> oc.getName().equals(name)).findFirst();
        if (outcomeOpt.isPresent()) outcome = outcomeOpt.get();
        
        return outcome;
    }
    
    
    private ActivityTypeLightweight type(List<ActivityType> types, String name) {
        ActivityTypeLightweight type = null;
        
        Optional<ActivityType> typeOpt = types.stream().filter(oc -> oc.getName().equals(name)).findFirst();
        if (typeOpt.isPresent()) {
            ActivityType tt = typeOpt.get();
            type = new ActivityTypeLightweight();
            type.setId(tt.getId());
            type.setName(tt.getName());
        }
        
        return type;
    }
}