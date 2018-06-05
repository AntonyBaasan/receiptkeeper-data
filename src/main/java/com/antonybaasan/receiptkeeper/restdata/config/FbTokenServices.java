package com.antonybaasan.receiptkeeper.restdata.config;

import com.antonybaasan.receiptkeeper.restdata.security.FbAuthentication;
import com.antonybaasan.receiptkeeper.restdata.security.FbTokenValidator;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FbTokenServices implements ResourceServerTokenServices {

    private FbTokenValidator tokenValidator;

    public FbTokenServices(FbTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String token) throws AuthenticationException, InvalidTokenException {
        OAuth2Request request = this.createRequest();

        FbUserInfo user = this.tokenValidator.validate(token);
        FbAuthentication authentication = new FbAuthentication(user);

        return new OAuth2Authentication(request, authentication);
    }

    private OAuth2Request createRequest(){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        Map<String, String> requestParameters = new HashMap<>();
        String clientId = "acme";
        boolean approved = true;
        Set<String> scope = new HashSet<>();
        scope.add("scope");
        Set<String> resourceIds = new HashSet<>();
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");
        Map<String, Serializable> extensionProperties = new HashMap<>();

        return new OAuth2Request(requestParameters, clientId,
                authorities, approved, scope,
                resourceIds, null, responseTypes, extensionProperties);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String token) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

}
