package com.pharmaconnect.pharma.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pharmaconnect.pharma.model.JwtAuthenticationResponseStore;
import com.pharmaconnect.pharma.model.JwtAuthenticationResponseUser;
import com.pharmaconnect.pharma.model.SignInStoreRequest;
import com.pharmaconnect.pharma.model.SignInUserRequest;
import com.pharmaconnect.pharma.model.SignUpStoreRequest;
import com.pharmaconnect.pharma.model.SignUpUserRequest;
import com.pharmaconnect.pharma.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/user")
    public ResponseEntity<JwtAuthenticationResponseUser> signUpUser(@RequestBody SignUpUserRequest request) {
        ResponseEntity<JwtAuthenticationResponseUser> jwtAuthenticationResponseUser=authenticationService.signUpUser(request);
        return jwtAuthenticationResponseUser;
    }

    @PostMapping("/signin/user")
    public JwtAuthenticationResponseUser signInUser(@RequestBody SignInUserRequest request) {
        return authenticationService.signInUser(request);
    }

    @PostMapping("/signup/store")
    public JwtAuthenticationResponseStore signUpStore(@RequestBody SignUpStoreRequest request) {
        return authenticationService.signUpStore(request);
    }

    @PostMapping("/signin/store")
    public JwtAuthenticationResponseStore signInStore(@RequestBody SignInStoreRequest request) {
        return authenticationService.signInStore(request);
    }

}
