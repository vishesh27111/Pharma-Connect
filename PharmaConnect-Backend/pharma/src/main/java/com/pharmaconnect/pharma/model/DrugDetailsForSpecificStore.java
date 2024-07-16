package com.pharmaconnect.pharma.model;


import java.sql.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class DrugDetailsForSpecificStore {

    private Integer drugId;
    private String drug_name;
    private String code;
    private Float unit_price;
    private String company_name;
    private Date production_date;
    private Date expiration_date;
    private Boolean prescription;
    private String description;
    private Integer quantity;
    
}
