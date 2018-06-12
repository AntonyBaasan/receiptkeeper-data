package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Page<Receipt> findAll(Specification<Receipt> spec, Pageable pageable);

    Page<Receipt> findByOwner(String ownerId, Pageable pageable);

    List<Receipt> findByIdIn(List<Long> ids);

    int deleteAllByIdIn(List<Long> ids);
}
