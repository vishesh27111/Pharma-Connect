package com.pharmaconnect.pharma.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Embeddable
@Data
@NoArgsConstructor
@Builder
@Component
public class StoreDrug implements Serializable {

    @Column(name = "DRUG_ID")
    private Integer drugId;
    @Column(name = "STORE_ID")
    private Integer storeId; 
}
