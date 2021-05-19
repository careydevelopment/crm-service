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

import com.careydevelopment.crm.model.SalesType;
import com.careydevelopment.crm.repository.SalesTypeRepository;

@RestController
@RequestMapping("/salestypes")
public class SalesTypeController {
    
    private static final Logger LOG = LoggerFactory.getLogger(SalesTypeController.class);
        

    @Autowired
    private SalesTypeRepository salesTypeRepository;
    
    
    @GetMapping("")
    public ResponseEntity<?> search() {
        List<SalesType> salesTypes = salesTypeRepository.findAll();
        return ResponseEntity.ok(salesTypes);
    }
}
