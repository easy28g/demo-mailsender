package com.example.mailsenderdemo.controllers;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import com.example.mailsenderdemo.models.Role;
import com.example.mailsenderdemo.models.User;
import com.example.mailsenderdemo.services.MailSender;
import com.example.mailsenderdemo.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;
    
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
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setActivatedCode(UUID.randomUUID().toString());
        userService.saveNewUser(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                "Hello, %s! \n" + 
                "Welcom to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivatedCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        
        if(isActivated){
            model.addAttribute("message", "User successfully activated!");
        }else{
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }

}
