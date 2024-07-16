package com.pharmaconnect.pharma.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaconnect.pharma.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store,Long> {
     Optional <Store> findByEmail(String email);
     Store findByStoreId(Long storeId);
     
}
