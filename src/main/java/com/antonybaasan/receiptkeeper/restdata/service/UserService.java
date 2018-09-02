package com.antonybaasan.receiptkeeper.restdata.service;

import com.antonybaasan.receiptkeeper.restdata.model.User;

public interface UserService {
    User getUserById(Long ownerId);
    User getUserById(String ownerId);
}
