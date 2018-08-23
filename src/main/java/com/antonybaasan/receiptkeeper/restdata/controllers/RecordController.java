package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.services.RecordService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RecordController {
    private RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public Page<Record> getRecords(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") Date endDate,
            Pageable pageable) {

        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);

        return recordService.getRecords(text, startDate, endDate, pageable);
    }

    @RequestMapping(value = "/records/{id}", method = RequestMethod.GET)
    public ResponseEntity<Record> getRecord(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(this.recordService.getRecord(id), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/records", method = RequestMethod.POST)
    public ResponseEntity<Record> create(@RequestBody Record record) {
        return new ResponseEntity<>(this.recordService.create(record), HttpStatus.OK);
    }

    @RequestMapping(value = "/records", method = RequestMethod.PUT)
    public ResponseEntity<Record> udpate(@RequestBody Record receipt) {
        try {
            return new ResponseEntity<>(this.recordService.update(receipt), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/records", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMany(@RequestBody List<Long> ids) {
        try {
            int deletedCount = this.recordService.deleteRecords(ids);
            return new ResponseEntity<>(String.format("Deleted " + deletedCount + " of " + ids.size()), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RequestMapping(value = "/records/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Record> delete(@PathVariable("id") Long id) {

        try {
            Record oldReceipt = recordService.deleteRecord(id);
            return new ResponseEntity<>(oldReceipt, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}
