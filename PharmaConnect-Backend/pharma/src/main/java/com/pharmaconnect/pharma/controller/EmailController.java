package com.pharmaconnect.pharma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.pharmaconnect.pharma.model.EmailDetails;
import com.pharmaconnect.pharma.service.EmailService;
 
// Annotation
@RestController
// Class
public class EmailController {
 
    @Autowired 
    private EmailService emailService;
 
    // Sending a simple Email
    @PostMapping("/user/sendMail")
    public String sendMail(@RequestBody EmailDetails details)
    {
        return emailService.sendBloodRegistrationDetailsMail(details);
    }
 
   
}