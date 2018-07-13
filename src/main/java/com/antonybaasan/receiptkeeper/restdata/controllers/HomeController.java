package com.antonybaasan.receiptkeeper.restdata.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String ok() {

        return "OK";
    }
}
