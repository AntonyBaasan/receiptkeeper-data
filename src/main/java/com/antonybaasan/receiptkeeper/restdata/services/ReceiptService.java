package com.antonybaasan.receiptkeeper.restdata.services;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import javassist.NotFoundException;

import java.util.Optional;

public class ReceiptService {

    private ReceiptRepository repository;
    private AuthFacade auth;

    public ReceiptService(ReceiptRepository repository, AuthFacade auth) {
        this.repository = repository;
        this.auth = auth;
    }

    public void delete(Long id) throws IllegalAccessException, NotFoundException {

        String currentUserId = auth.getUser().getUid();

        Optional<Receipt> oldReceipt = this.repository.findById(id);
        if (!oldReceipt.isPresent()) {
            throw new NotFoundException("Can't find receipt");
        }

        if (!oldReceipt.get().getOwner().equals(currentUserId)) {
            throw new IllegalAccessException("User doens't have this receipt");
        }

        this.repository.delete(oldReceipt.get());
    }
}
