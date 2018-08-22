package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Page<Record> findAll(Specification<Record> spec, Pageable pageable);

    List<Record> findByIdIn(List<Long> ids);

    int deleteAllByIdIn(List<Long> ids);
}
