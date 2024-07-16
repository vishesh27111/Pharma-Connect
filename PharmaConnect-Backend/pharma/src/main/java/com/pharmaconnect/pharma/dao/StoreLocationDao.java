package com.pharmaconnect.pharma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pharmaconnect.pharma.entity.StoreLocation;

@Repository
public interface StoreLocationDao extends JpaRepository<StoreLocation,Integer> {
  
}
