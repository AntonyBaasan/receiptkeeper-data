package com.antonybaasan.receiptkeeper.restdata.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {
    @RequestMapping("/items")
    public String getAllItems() {
        return "hello world";
    }
}
