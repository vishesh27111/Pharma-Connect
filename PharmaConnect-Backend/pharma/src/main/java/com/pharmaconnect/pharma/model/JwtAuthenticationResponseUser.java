package com.pharmaconnect.pharma.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponseUser {
  String errorMessage;
  String token;
  String emailId;
  Long userId;
}
