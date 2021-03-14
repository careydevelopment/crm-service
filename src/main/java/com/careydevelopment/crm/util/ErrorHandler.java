package com.careydevelopment.crm.util;

import com.careydevelopment.crm.model.ErrorResponse;
import com.careydevelopment.crm.model.ValidationError;

public class ErrorHandler {
    
    public static void addError(ErrorResponse errorResponse, String errorMessage, String field, String code) {        
        ValidationError validationError = new ValidationError();
        validationError.setCode(code);
        validationError.setDefaultMessage(errorMessage);
        validationError.setField(field);
        
        errorResponse.getErrors().add(validationError);
    }

}
