package com.pharmaconnect.pharma.model;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEmailDetails {

    private String customerName;
    private String medicineName;
    private String quantity;
    private String reservationTime;
    private String storeName;
    private String storeAddress;
    private String userEmail;
    private Float totalPrice;
    private LocalDateTime date;
    private Integer reservationId;
    
}
