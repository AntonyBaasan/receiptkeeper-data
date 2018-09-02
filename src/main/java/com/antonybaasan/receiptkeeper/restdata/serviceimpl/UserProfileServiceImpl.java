package com.antonybaasan.receiptkeeper.restdata.serviceimpl;

import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;
import com.antonybaasan.receiptkeeper.restdata.repositories.UserProfileRepository;
import com.antonybaasan.receiptkeeper.restdata.service.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile getUserById(Long ownerId) {
        return userProfileRepository.findById(ownerId).get();
    }

    @Override
    public UserProfile getUserById(String ownerId) {
        long id = Long.parseLong(ownerId);
        return getUserById(id);
    }
}
