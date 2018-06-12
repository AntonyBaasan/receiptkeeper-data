package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.utils.ReceiptSpecification;
import com.antonybaasan.receiptkeeper.restdata.utils.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ReceiptController {
    private ReceiptRepository repository;
    private AuthFacade auth;

    public ReceiptController(ReceiptRepository repository, AuthFacade auth) {
        this.repository = repository;
        this.auth = auth;
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.GET)
    public Page<Receipt> getReceipts(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) @DateTimeFormat(pattern="MM/dd/yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern="MM/dd/yyyy") Date endDate,
            Pageable pageable) {

        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        String ownerId = auth.getUser().getUid();

        Specification<Receipt> spec = Specification.where(new ReceiptSpecification(new SearchCriteria("owner", "=", ownerId)));
        spec = this.updateSpec(spec, text, startDate, endDate);

        Page<Receipt> all = this.repository.findAll(spec, pageable);
        return all;
    }

    private Specification<Receipt> updateSpec(Specification<Receipt> spec, String text, Date startDate, Date endDate){
        if (text != null) {
            Specification<Receipt> specOr = Specification.where(new ReceiptSpecification(new SearchCriteria("title", ":", text)))
                    .or(new ReceiptSpecification(new SearchCriteria("description", ":", text)));
            spec = spec.and(specOr);
        }
        if (startDate != null) {
            spec = spec.and(new ReceiptSpecification(new SearchCriteria("date", ">", startDate)));
        }
        if (endDate != null) {
            spec = spec.and(new ReceiptSpecification(new SearchCriteria("date", "<", endDate)));
        }

        return spec;
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
        Optional<Receipt> oldReceipt = this.repository.findById(receipt.getId());
        if (!oldReceipt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!oldReceipt.get().getOwner().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return new ResponseEntity<>(this.repository.save(receipt), HttpStatus.OK);
    }

    @RequestMapping(value = "/receipts/selections", method = RequestMethod.DELETE)
    public ResponseEntity<String> udpateMany(@RequestBody List<Long> ids) {

        String currentUserId = auth.getUser().getUid();

        List<Receipt> receipts = this.repository.findByIdIn(ids);

        Optional<Receipt> invalidReceipt = receipts
                .stream()
                .filter(r -> !r.getOwner().equals(currentUserId))
                .findAny();

        if (invalidReceipt.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int deletedCount = this.repository.deleteAllByIdIn(ids);

        return new ResponseEntity<String>(String.format("Deleted " + deletedCount + " of " + ids.size()), HttpStatus.OK);
    }

    @RequestMapping(value = "/receipts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Receipt> delete(@PathVariable("id") Long id) {

        String currentUserId = auth.getUser().getUid();
        Optional<Receipt> oldReceipt = this.repository.findById(id);
        if (!oldReceipt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!oldReceipt.get().getOwner().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        this.repository.deleteById(id);
        return new ResponseEntity<>(oldReceipt.get(), HttpStatus.OK);
    }

}
