package com.careydevelopment.crm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Account;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.CurrencyType;
import com.careydevelopment.crm.model.Deal;
import com.careydevelopment.crm.model.DealStage;
import com.careydevelopment.crm.model.LineOfBusiness;
import com.careydevelopment.crm.model.Price;
import com.careydevelopment.crm.model.PriceType;
import com.careydevelopment.crm.model.Product;
import com.careydevelopment.crm.model.ProductType;
import com.careydevelopment.crm.model.UnitType;
import com.careydevelopment.crm.repository.DealRepository;
import com.careydevelopment.crm.repository.DealStageRepository;

@Component
public class ApplicationListenerInitialize implements ApplicationListener<ApplicationReadyEvent>  {
    
    
    private String data = "";

    @Autowired
    private DealStageRepository dealStageRepo;
    
    @Autowired
    private DealRepository dealRepo;
    
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
}