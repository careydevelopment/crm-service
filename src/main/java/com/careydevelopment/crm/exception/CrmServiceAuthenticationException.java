package com.careydevelopment.crm.exception;

import org.springframework.security.core.AuthenticationException;

public class CrmServiceAuthenticationException extends AuthenticationException {
    
    private static final long serialVersionUID = 6356844005269578058L;

    public CrmServiceAuthenticationException(String s) {
        super(s);
    }
}
