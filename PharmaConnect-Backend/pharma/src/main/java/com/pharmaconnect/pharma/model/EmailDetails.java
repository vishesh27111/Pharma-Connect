package com.pharmaconnect.pharma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
 
// Class
public class EmailDetails {
 
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String firstName;
    private String lastName;
    private String address;
    private String bloodGroup;
    private String phone;
    private String age;
    private String email;
    private Boolean donatedPreviously;
}