package com.careydevelopment.crm.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.model.Contact;
import com.careydevelopment.crm.model.SalesOwner;

@Component
public class SecurityUtil {

    public boolean isAuthorizedToAccessContact(Contact contact) {
        boolean authorized = false;

        if (contact != null) {
            String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            if (username != null) {
                if (contact.getSalesOwner() != null && contact.getSalesOwner().getUsername() != null) {
                    if (username.equals(contact.getSalesOwner().getUsername())) {
                        authorized = true;
                    } 
                } 
            } 
        }
        
        return authorized;
    }

    
    public boolean isAuthorizedToAccessUser(SalesOwner salesOwner) {
        boolean authorized = false;

        if (salesOwner != null) {
            String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            if (username != null) {
                if (salesOwner.getUsername() != null) {
                    if (username.equals(salesOwner.getUsername())) {
                        authorized = true;
                    } 
                } 
            } 
        }
        
        return authorized;
    }
    
//    public boolean isAuthorizedToAccessAccount(String id) {
//        boolean authorized = false;
//        
//        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        
//        if (username != null) {
//            Optional<Account> existingAccountOpt = accountRepository.findById(id);
//            
//            if (existingAccountOpt.isPresent()) {
//                Account existingAccount = existingAccountOpt.get();
//                
//                if (existingAccount.getSalesOwner() != null && existingAccount.getSalesOwner().getUsername() != null) {
//                    if (username.equals(existingAccount.getSalesOwner().getUsername())) {
//                        authorized = true;
//                    } 
//                } 
//            } 
//        } 
//        
//        return authorized;
//    }
}
