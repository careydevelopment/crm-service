package com.careydevelopment.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import us.careydevelopment.ecosystem.jwt.config.JwtOnlySecurityConfig;

@Configuration
@EnableWebSecurity
public class CrmSecurityConfig extends JwtOnlySecurityConfig {

    
    public CrmSecurityConfig(@Autowired CrmJwtAuthenticationProvider jwtAuthenticationProvider) {
        this.authenticationProvider = jwtAuthenticationProvider;
    }
   
}
