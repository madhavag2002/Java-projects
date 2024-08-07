package com.example.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.blog.Models.Account;
import com.example.blog.Service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;







@Controller
public class AccountController {
    @Autowired
    private AccountService accountservice;

    @GetMapping("/register")
    public String register(Model model)
    {
        Account account=new Account();
        model.addAttribute("account", account);
        return "register";

    }
    //binding result check all the errors if any during registering and stores the error in result
    @PostMapping("/register")
    public String register_user(@Valid @ModelAttribute Account account, BindingResult result){
        if (result.hasErrors()){       //if there is any error we redirect to register page displaying some msg 
            return "register";
        }
        
        accountservice.save(account);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(Model model)
    {
       return "login";
    }
    @GetMapping("/profile")
    public String profile(Model model)
    {
       return "profile";
    }
    @GetMapping("/forget-password")
    public String forget(Model model)
    {
       return "forget_password";
    }
    
    
}
