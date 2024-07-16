package com.pharmaconnect.pharma.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaconnect.pharma.service.StoreInfoService;
import com.pharmaconnect.pharma.service.StoreService;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.model.DrugDetailsForSpecificStore;
import com.pharmaconnect.pharma.model.StoreDetailsForSpecificDrug;

@RestController
@RequestMapping(path = "/store")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    StoreInfoService storeInfoService;

    @Autowired
    StoreService storeService;

    @GetMapping(path = "/profile/{storeId}")
    public Store findByStoreId(@PathVariable Long storeId) {
        Store store = null;
        try {
            return storeInfoService.findByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    @GetMapping(path = "/availableDrugsForStore/{storeId}")
    public List<DrugDetailsForSpecificStore> findDrugDetailsForSpecificStore(@PathVariable Long storeId) {
        return storeInfoService.findDrugDetailsForSpecificStore(storeId);
    }

    @GetMapping(path = "/availableStoresForDrug/{drugId}")
    public List<StoreDetailsForSpecificDrug> findStoreDetailsForSpecificDrug(@PathVariable Long drugId) {
        return storeInfoService.findStoreDetailsForSpecificDrug(drugId);
    }

    @PostMapping(path = "/update/profile")
    public ResponseEntity<String> updateStoreProfile(@RequestBody(required = true) Map<String, String> requestMap) {
        return storeService.updateStoreProfile(requestMap);
    }

    @PostMapping(path = "/update/password")
    public ResponseEntity<String> updateStorePassword(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return storeService.updateStorePassword(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"message\":\"" + "Something went wrong" + "\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
