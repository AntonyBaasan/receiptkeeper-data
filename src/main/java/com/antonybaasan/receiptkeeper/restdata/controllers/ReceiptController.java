package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceiptController {
    private ReceiptRepository repository;
    private AuthFacade auth;

    public ReceiptController(ReceiptRepository repository, AuthFacade auth) {
        this.repository = repository;
        this.auth = auth;
    }

    @RequestMapping("/r")
    public Object getReceipts() {

        String ownerId = auth.getUser().getUid();
//        Object all = this.repository.findAll();
        Object all = this.repository.findByOwner(ownerId);
        return all;
    }

}
