package com.example.mailsenderdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mailsenderdemo.dao.UserRepo;
import com.example.mailsenderdemo.models.User;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User saveNewUser(User user){
        return userRepo.save(user);
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    };
    
}
