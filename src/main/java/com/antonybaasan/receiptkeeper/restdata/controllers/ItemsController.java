package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

    @Autowired
    private AuthFacade authFacade;

    @RequestMapping("/items")
    public String getAllItems() throws Exception {

        FbUserInfo user = this.authFacade.getUser();
        if (user == null)
            throw new Exception("Invalid user information!");

        String username = user.getName();
        String email = user.getEmail();
        return "hello world, " + username + ", " + email;
    }
}
