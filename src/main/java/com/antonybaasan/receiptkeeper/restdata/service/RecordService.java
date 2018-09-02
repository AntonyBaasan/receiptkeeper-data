package com.antonybaasan.receiptkeeper.restdata.service;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RecordService {
    Page<Record> getRecords(String filterText, Date startDate, Date endDate, Pageable pageable);

    Record create(Record record);

    Record update(Record record) throws IllegalAccessException, NotFoundException;

    Record getRecord(long id) throws IllegalAccessException;

    int deleteRecords(List<Long> ids) throws IllegalAccessException;

    Record deleteRecord(Long id) throws NotFoundException, IllegalAccessException;
}
