package com.careydevelopment.crm.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Account;
import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.Deal;
import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.model.Product;
import com.careydevelopment.crm.model.SalesOwner;
import com.careydevelopment.crm.service.ProductService;
import com.careydevelopment.crm.service.ServiceException;

@Component
public class DealValidator {

    private static final Logger LOG = LoggerFactory.getLogger(DealValidator.class);
    
    
    @Autowired
    private ContactValidator contactValidator;
    
    @Autowired
    private ProductService productService;
    
    
    
    public ErrorResponse validateDeal(Deal deal, String bearerToken) {
        ErrorResponse errorResponse = new ErrorResponse();        

        deal = (Deal)SpaceUtil.trimReflective(deal);
        
        contactValidator.validateContact(deal.getContact(), errorResponse, bearerToken);
        validateProduct(deal.getProduct(), errorResponse, bearerToken);
        
        if (errorResponse.getErrors().size() == 0) errorResponse = null;
        return errorResponse;
    }
    
    
    private void validateProduct(Product product, ErrorResponse errorResponse, String bearerToken) {
        if (!StringUtils.isBlank(product.getId())) {
            try {
                Product fetchedProduct = productService.fetchProduct(bearerToken, product.getId());
                
                //rather than validate all this data, just set it from the source of truth
                product.setDescription(fetchedProduct.getDescription());
                product.setLineOfBusiness(fetchedProduct.getLineOfBusiness());
                product.setName(fetchedProduct.getName());
                product.setPrices(fetchedProduct.getPrices());
                product.setProductType(fetchedProduct.getProductType());
            } catch (ServiceException se) {
                LOG.error("Problem fetching product!", se);
                
                if (se.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                    ErrorHandler.addError(errorResponse, "Invalid product ID: " + product.getId(), "product", "invalidProductId");
                } else {
                    ErrorHandler.addError(errorResponse, "Problem retrieving product " + product.getId(), "product", "internalError");
                }
            }      
        }
    }
}
