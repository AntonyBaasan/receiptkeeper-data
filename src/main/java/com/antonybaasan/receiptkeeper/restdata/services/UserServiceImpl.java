package com.antonybaasan.receiptkeeper.restdata.services;

import com.antonybaasan.receiptkeeper.restdata.model.User;
import com.antonybaasan.receiptkeeper.restdata.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User getUserById(Long ownerId) {
        return usersRepository.findById(ownerId).get();
    }

    @Override
    public User getUserById(String ownerId) {
        long id = Long.parseLong(ownerId);
        return getUserById(id);
    }
}
