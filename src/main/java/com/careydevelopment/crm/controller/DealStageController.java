package com.careydevelopment.crm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.DealStage;
import com.careydevelopment.crm.repository.DealStageRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dealstages")
public class DealStageController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DealStageController.class);
        

    @Autowired
    private DealStageRepository dealStageRepository;
    
    
    @GetMapping("")
    public ResponseEntity<?> fetchAllStagesByStageType(@RequestParam String salesType) {
        List<DealStage> dealStages = dealStageRepository.findAllBySalesTypeOrderByIndexAsc(salesType);
        return ResponseEntity.ok(dealStages);
    }
}
