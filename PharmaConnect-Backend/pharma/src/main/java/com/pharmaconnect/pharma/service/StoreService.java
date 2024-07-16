package com.pharmaconnect.pharma.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.model.WorkingHours;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StoreService implements UserDetailsService{

    protected Logger log = LoggerFactory.getLogger(StoreService.class);
   

    @Autowired
    private  StoreDao storeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Store save(Store newStore) {
        log.info("-----save called----");
        if (newStore.getStoreId() == null) {
        newStore.setCreatedAt(LocalDateTime.now());
        }

        newStore.setUpdatedAt(LocalDateTime.now());
        return storeRepository.save(newStore);
  }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return storeRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Store not found"));
    }

    public ResponseEntity<String> updateStorePassword(Map<String, String> requestMap) {
        log.info("-----updateStorePassword called------");
        if (validateStorePasswordDetails(requestMap)) {
            Long storeId = Long.valueOf(requestMap.get("storeId"));
            String currentPassword = requestMap.get("currentPassword");
            String newPassword = requestMap.get("newPassword");
            Optional<Store> optional = storeRepository.findById(storeId);

            if (optional.isPresent()) {
                Store store = optional.get();

                // Check if the current password matches
                if (passwordEncoder.matches(currentPassword, store.getPassword())) {
                    // Update the password
                    store.setPassword(passwordEncoder.encode(newPassword));
                    storeRepository.save(store);

                    return new ResponseEntity<>("{\"message\":\"Store Password Updated Successfully\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Current password is incorrect\"}", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"Store not found in the database.\"}", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean validateStorePasswordDetails(Map<String, String> requestMap) {
        boolean id=requestMap.containsKey("storeId") ;
        boolean currentPass=requestMap.containsKey("currentPassword");
        boolean newPass=requestMap.containsKey("newPassword");
        return id && currentPass && newPass;
    }

    public ResponseEntity<String> updateStoreProfile(Map<String, String> requestMap) {
        log.info("-----updateStoreProfile called------");

        if (validateStoreDetails(requestMap)) {
            Long storeId=Long.valueOf(requestMap.get("storeId"));
            Optional<Store> optional = storeRepository.findById(storeId);
    
            if (optional.isPresent()) {
                Store store = optional.get();
                store.setEmail(requestMap.get("email"));
                store.setAddress(requestMap.get("address"));
                store.setManager_name(requestMap.get("manager_name"));
                // store.setPassword(passwordEncoder.encode(optional.get().getPassword()));
                store.setPassword(optional.get().getPassword());
                store.setPhone(requestMap.get("phone"));
                store.setRegistration_number(requestMap.get("registration_number"));
                store.setStore_name(requestMap.get("store_name"));
                store.setZipcode(requestMap.get("zipcode"));
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());              
                WorkingHours workingHours=new WorkingHours();
                workingHours.setMonday( requestMap.get("monday"));
                workingHours.setTuesday(requestMap.get("tuesday"));
                workingHours.setWednesday(requestMap.get("wednesday"));  
                workingHours.setThursday(requestMap.get("thursday")); 
                workingHours.setFriday(requestMap.get("friday"));
                workingHours.setSaturday(requestMap.get("saturday"));
                workingHours.setSunday(requestMap.get("sunday"));
                try {
                    String workingHoursJson = objectMapper.writeValueAsString(workingHours);
                    store.setWorking_hours(workingHoursJson);
    
                } catch (JsonProcessingException e) {
                    return new ResponseEntity<>("{\"message\":\"JSON Parsing Exception.\"}", HttpStatus.BAD_REQUEST);
                }
                
                // Parse updatedAt and createdAt using DateTimeFormatter.ISO_LOCAL_DATE_TIME
                try {
                    LocalDateTime updatedDateTime = LocalDateTime.parse(requestMap.get("updatedAt"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    store.setCreatedAt(optional.get().getCreatedAt());
                    store.setUpdatedAt(updatedDateTime);
                    
                } catch (DateTimeParseException e) {
                    return new ResponseEntity<>("{\"message\":\"Invalid date format.\"}", HttpStatus.BAD_REQUEST);
                }

                storeRepository.save(store);
    
                return new ResponseEntity<>("{\"message\":\"Store Details Updated Successfully\"}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"message\":\"Store not found in the database.\"}", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
        }
    }


   
    public boolean validateStoreDetails(Map<String, String> requestMap) {
        boolean storeId=requestMap.containsKey("storeId") ;
        boolean email=requestMap.containsKey("email");
        if (storeId
                && email
                && requestMap.containsKey("address") 
                && requestMap.containsKey("manager_name")
                && requestMap.containsKey("phone")
                && requestMap.containsKey("registration_number")
                && requestMap.containsKey("store_name")
                && requestMap.containsKey("updatedAt")
                && requestMap.containsKey("monday")
                && requestMap.containsKey("tuesday")
                && requestMap.containsKey("wednesday")
                && requestMap.containsKey("thursday")
                && requestMap.containsKey("friday")
                && requestMap.containsKey("saturday")
                && requestMap.containsKey("sunday")
                ) 
                {
            return true;
        }
    
        return false;
    }
}
