package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByOwner(String ownerId);
}
