package com.careydevelopment.crm.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.careydevelopment.crm.model.Product;

import reactor.util.retry.Retry;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    
    private WebClient productClient; 
    
    
    public ProductService(@Value("${ecosystem-product-service.endpoint}") String endpoint) {
        productClient = WebClient
                    .builder()
                    .baseUrl(endpoint)
                    .filter(WebClientFilter.logRequest())
                    .filter(WebClientFilter.logResponse())
                    .filter(WebClientFilter.handleError())
                    .build();
    }
    
    
    public Product fetchProduct(String bearerToken, String productId) {
        Product product = productClient.get()
                .uri("/products/" + productId)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .retrieve()
                .bodyToMono(Product.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(ex -> WebClientFilter.is5xxException(ex))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> 
                            new ServiceException("Max retry attempts reached", HttpStatus.SERVICE_UNAVAILABLE.value())))
                .block();
        
        LOG.debug("Product is " + product);
        
        return product;
    }
}
