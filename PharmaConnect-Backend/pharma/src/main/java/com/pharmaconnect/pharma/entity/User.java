package com.pharmaconnect.pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Date;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "USERS")
public class User implements UserDetails{

    private static final long serialVersionUID = 1L;
    public static String ROLE ="ROLE_USER" ;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID",nullable = false)
    private Long userId;  

    @Column(name="NAME",nullable = false)
    private String name;

    @Column(name="EMAIL_ID",nullable = false, unique = true)
    private String email;

    @Column(name="PASSWORD",nullable = false)
    private String password;

   @Column(name="DATE_OF_BIRTH",nullable = false)
    private Date date_Of_Birth;

    @Column(name="PHONE",nullable = false)
    private String phone;

    @Column(name="ADDRESS",nullable = false)
    private String address;

    @Column(name="ZIPCODE",nullable = false)
    private String zipcode;

    @Column(name="CREATED_AT",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="UPDATED_AT",nullable = false)
    private LocalDateTime updatedAt;

  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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