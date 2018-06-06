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

    @RequestMapping("/receipts")
    public List<Receipt> getReceipts() {

        String ownerId = auth.getUser().getUid();
        List<Receipt> allObjects = this.repository.findAll();
        List<Receipt> all = this.repository.findByOwner(ownerId);
        return all;
    }

}
