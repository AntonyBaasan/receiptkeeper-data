package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptController {
    private ReceiptRepository repository;
    private AuthFacade auth;

    public ReceiptController(ReceiptRepository repository, AuthFacade auth) {
        this.repository = repository;
        this.auth = auth;
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.GET)
    public Page<Receipt> getReceipts(Pageable pageable) {

        String ownerId = auth.getUser().getUid();
        Page<Receipt> all = this.repository.findByOwner(ownerId, pageable);
        return all;
    }

    @RequestMapping(value = "/receipts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Receipt> getReceipt(@PathVariable("id") Long id) {

        String currentUserId = auth.getUser().getUid();
        Receipt receipt = this.repository.findById(id).get();

        if (!receipt.getOwner().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.POST)
    public ResponseEntity<Receipt> create(@RequestBody Receipt receipt) {

        String ownerId = auth.getUser().getUid();
        receipt.setOwner(ownerId);


        return new ResponseEntity<>(this.repository.save(receipt), HttpStatus.OK);
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.PUT)
    public ResponseEntity<Receipt> udpate(@RequestBody Receipt receipt) {

        String currentUserId = auth.getUser().getUid();
        Receipt oldReceipt = this.repository.findById(receipt.getId()).get();
        if (oldReceipt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!oldReceipt.getOwner().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return new ResponseEntity<>(this.repository.save(receipt), HttpStatus.OK);
    }

    @RequestMapping(value = "/receipts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Receipt> delete(@PathVariable("id") Long id) {

        String currentUserId = auth.getUser().getUid();
        Receipt oldReceipt = this.repository.findById(id).get();
        if (oldReceipt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!oldReceipt.getOwner().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        this.repository.deleteById(id);
        return new ResponseEntity<>(oldReceipt, HttpStatus.OK);
    }

}
