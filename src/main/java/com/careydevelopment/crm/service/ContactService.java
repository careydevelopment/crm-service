package com.careydevelopment.crm.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.careydevelopment.crm.model.Contact;

import reactor.util.retry.Retry;

@Service
public class ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactService.class);
    
    private WebClient customerClient; 
    
    
    public ContactService(@Value("${ecosystem-customer-service.endpoint}") String endpoint) {
        customerClient = WebClient
                    .builder()
                    .baseUrl(endpoint)
                    .filter(WebClientFilter.logRequest())
                    .filter(WebClientFilter.logResponse())
                    .filter(WebClientFilter.handleError())
                    .build();
    }
    
    
    public Contact fetchContact(String bearerToken, String contactId) {
        Contact contact = customerClient.get()
                .uri("/contact/" + contactId)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .retrieve()
                .bodyToMono(Contact.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(ex -> WebClientFilter.is5xxException(ex))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> 
                            new ServiceException("Max retry attempts reached", HttpStatus.SERVICE_UNAVAILABLE.value())))
                .block();
        
        LOG.debug("Contact is " + contact);
        
        return contact;
    }
    
}
