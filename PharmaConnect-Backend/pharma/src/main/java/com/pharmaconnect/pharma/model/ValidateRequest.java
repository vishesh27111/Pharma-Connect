package com.pharmaconnect.pharma.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ValidateRequest {
    private HttpStatus httpStatus;
    private String status;
    private String message;
}
