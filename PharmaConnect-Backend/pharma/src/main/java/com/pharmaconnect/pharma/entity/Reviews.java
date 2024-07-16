package com.pharmaconnect.pharma.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="REVIEWS")
@XmlRootElement(name="REVIEWS")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@NoArgsConstructor
@Data
public class Reviews implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private int reviewId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_ID")
    private int storeId;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "REVIEW_DATE")
    private Date reviewDate;
    
    @Column(name = "USER_ID")
     private Long userId;
}
