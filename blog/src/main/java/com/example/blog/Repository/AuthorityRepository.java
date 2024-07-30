package com.example.blog.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.Models.Authority;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
    
}
