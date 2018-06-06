package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReceiptRepository extends PagingAndSortingRepository<Receipt, Long> {
    List<Receipt> findByOwner(String ownerId);
}
