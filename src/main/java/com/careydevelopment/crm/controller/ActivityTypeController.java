package com.careydevelopment.crm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.crm.model.ActivityType;
import com.careydevelopment.crm.repository.ActivityTypeRepository;

@RestController
@RequestMapping("/activitytypes")
public class ActivityTypeController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ActivityTypeController.class);

    @Autowired
    private ActivityTypeRepository activityTypeRepository;
    
    
    @GetMapping("")
    public ResponseEntity<?> fetchAllActivityTypes() {
        List<ActivityType> types = activityTypeRepository.findAllByOrderByNameAsc();
        return ResponseEntity.ok(types);
    }
}
