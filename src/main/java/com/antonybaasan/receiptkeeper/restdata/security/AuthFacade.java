package com.antonybaasan.receiptkeeper.restdata.security;

import org.springframework.security.core.Authentication;

public interface AuthFacade {
    Authentication getAuthentication();
    FbUserInfo getUser();
}
