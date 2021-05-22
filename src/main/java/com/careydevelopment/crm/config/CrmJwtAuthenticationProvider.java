package com.careydevelopment.crm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.util.JwtUtil;

import us.careydevelopment.ecosystem.jwt.config.JwtOnlyAuthenticationProvider;

@Component
public class CrmJwtAuthenticationProvider extends JwtOnlyAuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CrmJwtAuthenticationProvider.class);

    
    public CrmJwtAuthenticationProvider(@Autowired JwtUtil jwtUtil) {
        //this.jwtUserDetailsService = jwtUserDetailsService;
        
        this.jwtUtil = jwtUtil;
    }
}
