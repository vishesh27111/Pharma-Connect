package com.pharmaconnect.pharma.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpStoreRequest {
    
    String store_name;
    String email;
    String password;
    String manager_name;
    String phone;
    String address;
    String zipcode;
    String registration_number;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
