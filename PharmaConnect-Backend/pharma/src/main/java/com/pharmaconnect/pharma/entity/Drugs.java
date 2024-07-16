package com.pharmaconnect.pharma.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRUGS")
@XmlRootElement(name="DRUGS")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Drugs implements Serializable {
    

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="DRUG_ID")
    private Integer drugId;

    @Column(name="DRUG_NAME")
    private String drug_name;

    @Column(name="CODE")
    private String code;

    @Column(name="UNIT_PRICE")
    private Float unit_price;

    @Column(name="COMPANY_NAME")
    private String company_name;

    @Column(name="PRODUCTION_DATE")
    private Date production_date;

    @Column(name="EXPIRATION_DATE")
    private Date expiration_date;

    @Column(name="PRESCRIPTION")
    private Boolean prescription;

    @Column(name="DESCRIPTION")
    private String description;

}
