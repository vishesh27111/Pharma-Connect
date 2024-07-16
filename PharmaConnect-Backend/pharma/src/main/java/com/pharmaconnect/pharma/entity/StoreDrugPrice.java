package com.pharmaconnect.pharma.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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
@Table(name="DRUG_STORE_PRICE")
@XmlRootElement(name="DRUG_STORE_PRICE")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StoreDrugPrice implements Serializable {
    

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StoreDrug storeDrugPriceId;

    @Column(name="CODE")
    private String code;

    @Column(name="UNIT_PRICE")
    private Float unit_price;

    @Column(name="QUANTITY")
    private Integer quantity;




}
