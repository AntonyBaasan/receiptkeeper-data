//package com.antonybaasan.receiptkeeper.restdata.repositories;
//
//import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
//import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
//import org.springframework.data.rest.core.annotation.HandleBeforeSave;
//import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
//import org.springframework.stereotype.Component;
//
//@RepositoryEventHandler(Receipt.class)
//@Component
//public class ReceiptEventHandler {
//
//    @Autowired
//    private AuthFacade authFacade;
//
//    @HandleBeforeCreate
//    public void handleBeforeCreate(Receipt receipt){
//        receipt.setOwner(this.authFacade.getUser().getUid());
//    }
//
//    @HandleBeforeSave
//    public void handleBeforeSave(Receipt receipt){
////        receipt.setUid(this.authFacade.getUser().getUid());
//    }
//}
