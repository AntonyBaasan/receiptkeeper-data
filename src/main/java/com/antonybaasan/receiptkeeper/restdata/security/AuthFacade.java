package com.antonybaasan.receiptkeeper.restdata.security;

import com.antonybaasan.receiptkeeper.restdata.config.FbTokenServices;
import org.springframework.security.core.Authentication;

public interface AuthFacade {
    Authentication getAuthentication();
    FbUserInfo getUser();
}
