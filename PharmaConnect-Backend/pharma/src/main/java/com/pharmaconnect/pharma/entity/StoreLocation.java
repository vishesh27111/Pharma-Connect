package com.pharmaconnect.pharma.entity;

import java.math.BigDecimal;
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
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="STORELOCATION")
@XmlRootElement(name="STORELOCATION")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StoreLocation implements Serializable {

   private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="location_id")
    private Integer location_id;

    @Column(name="store_id")
    private Integer store_id;

    @Column(name="store_name")
    private String store_name;

    @Column(name="store_number")
    private String store_number;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;
    
    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;        

    @Column(name="postcode")
    private String postcode;

    @Column(name="latitude")
    private BigDecimal latitude;

    @Column(name="longitude")
    private BigDecimal longitude;

}
