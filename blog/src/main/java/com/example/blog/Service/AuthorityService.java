package com.example.blog.Service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.Models.Authority;
import com.example.blog.Repository.AuthorityRepository;


@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority){
        return authorityRepository.save(authority);

    }

   public Optional<Authority> findById(Long id){
        return authorityRepository.findById(id);
    }


}

