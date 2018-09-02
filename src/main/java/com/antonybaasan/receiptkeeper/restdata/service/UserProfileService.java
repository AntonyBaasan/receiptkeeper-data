package com.antonybaasan.receiptkeeper.restdata.service;

import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;

public interface UserProfileService {
    UserProfile getUserById(Long ownerId);
    UserProfile getUserById(String ownerId);
}
