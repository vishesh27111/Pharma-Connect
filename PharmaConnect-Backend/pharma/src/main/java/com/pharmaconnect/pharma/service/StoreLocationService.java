package com.pharmaconnect.pharma.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaconnect.pharma.dao.StoreLocationDao;
import com.pharmaconnect.pharma.entity.StoreLocation;

@Service
public class StoreLocationService {


   protected Logger log = LoggerFactory.getLogger(StoreLocationService.class);
            
      @Autowired
      StoreLocationDao storelcoationDao;

      public List<StoreLocation> getAllLocations(){
        log.info("-----getAllLocations called-----");
                   
        try {

            return storelcoationDao.findAll();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
      }

}