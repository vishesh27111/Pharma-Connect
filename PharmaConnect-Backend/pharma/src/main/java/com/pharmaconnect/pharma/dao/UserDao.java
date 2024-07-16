package com.pharmaconnect.pharma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaconnect.pharma.entity.User;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    
    Optional <User> findByEmail(String email);
  
}
