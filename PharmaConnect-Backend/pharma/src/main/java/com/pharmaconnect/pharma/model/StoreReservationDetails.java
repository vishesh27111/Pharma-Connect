package com.pharmaconnect.pharma.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreReservationDetails {

    private Integer reservationId;
    private Long storeId;
    private Integer drugId;
    private String userEmail;
    private String status; 
    private LocalDateTime lockAcquiredTime;
    private LocalDateTime releaseTime;
    private Integer quantityNeeded;
    private Float unitPrice;
    private String userName;
    private String drugName;

}
