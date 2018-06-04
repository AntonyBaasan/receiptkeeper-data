package com.antonybaasan.receiptkeeper.restdata.config;

import com.antonybaasan.receiptkeeper.restdata.security.FbAuthentication;
import com.antonybaasan.receiptkeeper.restdata.security.FbTokenValidator;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public class FbTokenServices implements ResourceServerTokenServices {

    private FbTokenValidator tokenValidator;

    public FbTokenServices(FbTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String token) throws AuthenticationException, InvalidTokenException {
        try {
            OAuth2Request request = null;

            FbUserInfo user = this.tokenValidator.validate(token);
            FbAuthentication authentication = new FbAuthentication(user);

            return new OAuth2Authentication(request, authentication);

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String token) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}
