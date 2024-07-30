package com.example.blog.Config;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.blog.Models.Account;
import com.example.blog.Models.Authority;
import com.example.blog.Models.Post;
import com.example.blog.Service.AccountService;
import com.example.blog.Service.AuthorityService;
import com.example.blog.Service.PostService;
import com.example.blog.util.constants.Authorities;
import com.example.blog.util.constants.Roles;


@Component
public class SeedData implements CommandLineRunner{

    @Autowired
    private AuthorityService authorityservice;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountservice;
    @Override
    public void run(String... args) throws Exception {
       List<Post> posts = postService.getAll();

       for(Authorities auh:Authorities.values())
       {
        Authority authority =new Authority();
        authority.setId(auh.getId());
        authority.setName(auh.getAuthority());
        authorityservice.save(authority);
       }
        Account account01 = new Account();
       Account account02 = new Account();
       Account account03 = new Account();
       Account account04 = new Account();

       account01.setEmail("user@user.com");
       account01.setPassword("pass987");
       account01.setFirstname("User");
       account01.setLastname("lastname");


       account02.setEmail("admin@admin.com");
       account02.setPassword("pass987");
       account02.setFirstname("Admin");
       account02.setLastname("lastname");
       account02.setRole(Roles.ADMIN.getRole());

       account03.setEmail("editor@editor.com");
       account03.setPassword("pass987");
       account03.setFirstname("Editor");
       account03.setLastname("lastname");
       account03.setRole(Roles.EDITOR.getRole());

       account04.setEmail("super_editor@editor.com");
       account04.setPassword("pass987");
       account04.setFirstname("Editor");
       account04.setLastname("lastname");
       account04.setRole(Roles.EDITOR.getRole());
       Set<Authority> authorities = new HashSet<>();
       authorityservice.findById(Authorities.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
       authorityservice.findById(Authorities.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
       account04.setAuthorities(authorities);

       accountservice.save(account01);
       accountservice.save(account02);
       accountservice.save(account03);
       accountservice.save(account04);

       

       if (posts.size() == 0){
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("Post 01 body.....................");
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("Post 02 body.....................");
            post02.setAccount(account02);

            postService.save(post02);

       }
        
    }
    
}

