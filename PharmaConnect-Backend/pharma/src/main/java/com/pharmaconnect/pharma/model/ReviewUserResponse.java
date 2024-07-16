package com.pharmaconnect.pharma.model;
 
 
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUserResponse {
 
    private int reviewId;
    private String storeName;
    private int storeId;
    private int rating;
    private String message;
    private Date reviewDate;
    private Long userId;
    private String userName;
    
}