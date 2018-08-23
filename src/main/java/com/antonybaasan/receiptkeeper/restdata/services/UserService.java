package com.antonybaasan.receiptkeeper.restdata.services;

import com.antonybaasan.receiptkeeper.restdata.model.User;

public interface UserService {
    User getUserById(Long ownerId);
    User getUserById(String ownerId);
}
