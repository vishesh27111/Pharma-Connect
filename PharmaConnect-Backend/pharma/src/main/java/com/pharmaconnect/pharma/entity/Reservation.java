package com.pharmaconnect.pharma.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "RESERVATION")
@Entity
@DynamicUpdate
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "DRUG_ID")
    private Integer drugId;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "STATUS")
    private String status; // Hold, purchased, release

    @Column(name = "LOCK_ACQUIRED_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lockAcquiredTime;

    @Column(name = "LOCK_RELEASE_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @Column(name = "QUANTITY_NEEDED")
    private Integer quantityNeeded;

    @Column(name = "UNIT_PRICE")
    private Float unitPrice;


}