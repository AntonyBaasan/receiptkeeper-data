package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import org.springframework.data.repository.PagingAndSortingRepository;

//@RepositoryRestResource(collectionResourceRel = "receipts", path = "receipts")
public interface ReceiptRepository extends PagingAndSortingRepository<Receipt, Long> {
}
