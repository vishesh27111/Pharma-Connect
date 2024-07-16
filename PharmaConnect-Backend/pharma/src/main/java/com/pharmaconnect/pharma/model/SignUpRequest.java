package com.pharmaconnect.pharma.model;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  String name;
  String email;
  String password;
  Date date_Of_Birth;
  String phone;
  String address;
  String zipcode;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

}