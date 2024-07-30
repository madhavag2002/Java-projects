package com.example.blog.Repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.Models.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findOneByEmailIgnoreCase(String email);
    
}

