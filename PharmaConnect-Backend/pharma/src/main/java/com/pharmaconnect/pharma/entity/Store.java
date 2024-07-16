package com.pharmaconnect.pharma.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "STORE")
public class Store implements UserDetails {
    

     private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="STORE_ID")
    private Long storeId;  

    @Column(name="STORE_NAME", nullable = false)
    private String store_name;

    @Column(name="EMAIL_ID",nullable = false, unique = true)
    private String email;

    @Column(name="PASSWORD",nullable = false)
    private String password;

   @Column(name="MANAGER_NAME",nullable = false)
    private String manager_name;

    @Column(name="PHONE",nullable = false)
    private String phone;

    @Column(name="ADDRESS",nullable = false)
    private String address;

    @Column(name="ZIPCODE",nullable = false)
    private String zipcode;

    @Column(name="REGISTRATION_NUMBER",nullable = false)
    private String registration_number;

    @Column(name="CREATED_AT",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="UPDATED_AT",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="WORKING_HOURS",columnDefinition = "TEXT")
    private String working_hours;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

   
    
}
