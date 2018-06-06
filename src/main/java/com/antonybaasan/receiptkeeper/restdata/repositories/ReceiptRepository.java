package com.antonybaasan.receiptkeeper.restdata.repositories;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "receipts", path = "receipts")
public interface ReceiptRepository extends PagingAndSortingRepository<Receipt, Long> {
}
