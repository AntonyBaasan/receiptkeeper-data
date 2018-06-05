package com.antonybaasan.receiptkeeper.restdata.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component
public class FbAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass()))
            return null;

        OAuth2Authentication auth = (OAuth2Authentication) authentication;

        if (auth.getPrincipal() == null)
            throw new BadCredentialsException("Invalid token!");

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OAuth2Authentication.class);
    }
}
