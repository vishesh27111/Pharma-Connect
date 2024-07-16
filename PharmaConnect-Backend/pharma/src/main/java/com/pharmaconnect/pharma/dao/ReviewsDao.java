package com.pharmaconnect.pharma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmaconnect.pharma.entity.Reviews;

@Repository
public interface ReviewsDao extends JpaRepository<Reviews,Integer> {
    

   // Query to fetch specific store reviews
   @Query("SELECT r FROM Reviews r WHERE r.storeId = :storeId")
    List<Reviews> findByStore(@Param("storeId") int storeId);
    
}