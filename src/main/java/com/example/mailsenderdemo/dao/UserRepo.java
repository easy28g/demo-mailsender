package com.example.mailsenderdemo.dao;

import com.example.mailsenderdemo.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
