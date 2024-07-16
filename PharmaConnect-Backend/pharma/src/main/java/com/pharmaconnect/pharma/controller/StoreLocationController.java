package com.pharmaconnect.pharma.controller;

import com.pharmaconnect.pharma.entity.StoreLocation;
import com.pharmaconnect.pharma.service.StoreLocationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/storelocation")
@CrossOrigin(origins = "*")
public class StoreLocationController {

    @Autowired
    StoreLocationService storelocationservice;
    
    @GetMapping(path="/getAllLocations")
    public List<StoreLocation> getAllLocations(){
        return storelocationservice.getAllLocations();  
    }

}