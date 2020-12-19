package com.careydevelopment.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().and()
            .csrf().disable()
            .addFilter(bearerTokenAuthenticationFilter())
            .authorizeRequests() 
            .anyRequest().access("hasAuthority('CAREYDEVELOPMENT_CRM_USER')").and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    
    @Bean
    BearerTokenAuthenticationFilter bearerTokenAuthenticationFilter() throws Exception {
        BearerTokenAuthenticationFilter filter = new BearerTokenAuthenticationFilter(authenticationManager());
        filter.setAuthenticationEntryPoint(new JwtAuthenticationEntryPoint());
        filter.setBearerTokenResolver(new DefaultBearerTokenResolver());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
       
        return filter;
    }


    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());  
        };
    }
    
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
}
