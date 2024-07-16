package com.pharmaconnect.pharma.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class StoreDetailsForSpecificDrug {

    
    private Long drugId;
    private Integer storeId; 
    private String store_name;
    private String email;
    private String registration_number;
    private Float unit_price;
    private Integer quantity;
    private String manager_name;
    private String phone;
    private String address;
    private String zipcode ;
   
    
}
