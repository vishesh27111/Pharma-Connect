package com.pharmaconnect.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaconnect.pharma.entity.Reviews;
import com.pharmaconnect.pharma.service.ReviewsService;

@RestController
@RequestMapping(path = "/reviews")
@CrossOrigin(origins = "*")
public class ReviewsController {


    @Autowired
    ReviewsService reviewsService;

    @GetMapping(path="/getStoreReviews/{storeId}")
    public ResponseEntity<String> getStoreReviews(@PathVariable int storeId)
    {
        try {
            return reviewsService.getStoreReviews(storeId);
 
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
    };

    @PostMapping(path="/addStoreReviews")
    public Reviews addStoreReviews(@RequestBody(required = true) Reviews reviews)
    {

        try {
            return reviewsService.addStoReviews(reviews);
 
         } catch (Exception e) {
             e.printStackTrace();
         }
 
        return null;
     }

    
}
