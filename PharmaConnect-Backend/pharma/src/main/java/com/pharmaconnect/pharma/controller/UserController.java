package com.pharmaconnect.pharma.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pharmaconnect.pharma.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping(path = "/profile")
    public ResponseEntity<String> findUserByEmail(@RequestBody(required = true) Map<String, String> requestMap) {
        return userService.findUserByEmail(requestMap);
    }

    @PostMapping(path = "/update/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.updateUserProfile(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something went wrong" + "\"}",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update/password")
    public ResponseEntity<String> updatePassword(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.updateUserPassword(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"message\":\"" + "Something went wrong" + "\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
