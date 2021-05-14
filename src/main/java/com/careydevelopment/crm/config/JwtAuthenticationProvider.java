package com.careydevelopment.crm.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import com.careydevelopment.crm.exception.CrmServiceAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret = null;
    
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(BearerTokenAuthenticationToken.class);
    }
    
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearerToken = (BearerTokenAuthenticationToken) authentication;
        Authentication auth = null;
        
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(bearerToken.getToken());
            Claims claims = jwsClaims.getBody();
            Collection<? extends GrantedAuthority> authorities = getAuthorities(claims); 

            auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            LOG.debug("Authentication token: " + auth);            
        } catch (IllegalArgumentException e) {
            throw new CrmServiceAuthenticationException("Invalid token");
        } catch (ExpiredJwtException e) {
            throw new CrmServiceAuthenticationException("Token expired");
        } catch (SignatureException e) {
            throw new CrmServiceAuthenticationException("Invalid signature");
        }
        
        return auth;
    }
    
    
    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<String> authorityNames = (List<String>) claims.get("authorities");
        
        Collection<? extends GrantedAuthority> authorities = authorityNames
            .stream()
            .map(auth -> new SimpleGrantedAuthority(auth))
            .collect(Collectors.toList());

        return authorities;
    }
}
