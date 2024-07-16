package com.pharmaconnect.pharma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pharmaconnect.pharma.entity.Drugs;


@Repository
public interface DrugsDao extends JpaRepository<Drugs,Integer>{

    Drugs findByDrugId(Integer drug_id);
        
}
