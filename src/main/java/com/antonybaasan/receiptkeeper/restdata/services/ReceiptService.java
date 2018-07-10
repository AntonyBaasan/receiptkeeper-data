package com.antonybaasan.receiptkeeper.restdata.services;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import javassist.NotFoundException;
import java.util.Optional;

public interface ReceiptService {
    Optional<Receipt> delete(Long id) throws IllegalAccessException, NotFoundException;
}