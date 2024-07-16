package com.pharmaconnect.pharma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.authentication.AuthenticationProvider;

import com.pharmaconnect.pharma.filter.JwtAuthenticationFilter;
import com.pharmaconnect.pharma.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableTransactionManagement
public class SecurityConfig {


    private final JwtAuthenticationFilter JwtAuthenticationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final CorsConfig corsConfig;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider =new DaoAuthenticationProvider();     
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    
 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf
                        .disable()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize 
                        .requestMatchers(HttpMethod.POST, "/api/auth/v1/signup/**", "/api/auth/v1/signin/**","/user/*","/store/drug/**","/user/update/**","/reservation/user/drug-reserve","/reservation/store/drug-purchase","bookings/addBookings","/reviews/addStoreReviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/**", "/store/drug/**", "purchases/**","/user/profile/","/store/**","/store/*","/storelocation/*","/bookings/*", "/bookings/getBookingsByID/{id}","/purchases/getStorePurchases/{StoreID}","/reservation/user/reserved-bookings/{userEmail}","/reservation/store/reserved-bookings/{storeId}","/reviews/getStoreReviews/{storeId}", "/reviews/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/store/drug/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/user/profile/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/store/profile/**").permitAll()  // Exclude this API from authentication
                        .anyRequest().authenticated()     

                )
                .addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
