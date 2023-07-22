package com.example.mailsenderdemo.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mailsenderdemo.models.Role;
import com.example.mailsenderdemo.models.User;
import com.example.mailsenderdemo.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping({"/home", "/"})
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userFromDb = userService.findByUsername(user.getUsername());
        if(userFromDb != null){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveNewUser(user);
        return "redirect:/login";
    }

}
