package com.antonybaasan.receiptkeeper.restdata.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FbAuthentication implements Authentication {

    private FbUserInfo userInfo;
    private boolean authenticated;

    public FbAuthentication(FbUserInfo userInfo) {
        if (userInfo != null) {
            this.userInfo = userInfo;
            setAuthenticated(true);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return this.userInfo.getToken();
    }

    @Override
    public Object getDetails() {
        return this.userInfo;
    }

    @Override
    public Object getPrincipal() {
        return this.userInfo;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
         this.authenticated = isAuthenticated;
    }



    @Override
    public String getName() {
        return this.userInfo.getName();
    }
}
