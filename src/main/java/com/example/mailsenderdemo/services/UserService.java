package com.example.mailsenderdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mailsenderdemo.dao.UserRepo;
import com.example.mailsenderdemo.models.User;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    public User saveNewUser(User user){
        return userRepo.save(user);
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
    
}
