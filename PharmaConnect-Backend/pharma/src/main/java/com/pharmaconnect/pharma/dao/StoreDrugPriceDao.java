package com.pharmaconnect.pharma.dao;

import com.pharmaconnect.pharma.entity.StoreDrug;
import com.pharmaconnect.pharma.entity.StoreDrugPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface StoreDrugPriceDao extends JpaRepository<StoreDrugPrice,StoreDrug>{
  
   Optional<StoreDrugPrice> findByStoreDrugPriceId(StoreDrug storeDrug);

   

}
