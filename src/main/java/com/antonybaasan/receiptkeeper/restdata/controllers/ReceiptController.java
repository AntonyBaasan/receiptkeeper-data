package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceiptController {
    private ReceiptRepository repository;

    public ReceiptController(ReceiptRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/r")
    public Object getReceipts() {

        Object all = this.repository.findAll();
        return all;
    }

}
