package com.example.mailsenderdemo.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mailsenderdemo.models.User;
import com.example.mailsenderdemo.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping("/login")
    public String signedIn(User user, Model model){
        //v1
        User userdb = userService.findByUsername(user.getUsername());
        if(userdb != null){
            if(user.getUsername().equals(userdb.getUsername()) && user.getPassword().equals(userdb.getPassword())){
                return "home";
            }else{
                model.addAttribute("message", "Incorrect data entry!");
                return "login";
            }
        }else{
            model.addAttribute("message", "Incorrect data entry!");
            return "login";
        }
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userFromDb = userService.findByUsername(user.getUsername());
        if(userFromDb != null){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActivatedCode(UUID.randomUUID().toString());
        userService.saveNewUser(user);
        return "redirect:/login";
    }

}
