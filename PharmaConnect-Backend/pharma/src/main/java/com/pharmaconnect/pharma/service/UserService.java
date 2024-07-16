package com.pharmaconnect.pharma.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private  UserDao userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public User save(User newUser) {
        if (newUser.getUserId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<String> updateUserProfile(Map<String, String> requestMap) {

        if (validateUserDetails(requestMap)) {
            Optional<User> optional = userRepository.findByEmail(requestMap.get("email"));

            if (optional.isPresent()) {
                User user = optional.get();
                user.setAddress(requestMap.get("address"));
                user.setDate_Of_Birth(Date.valueOf(requestMap.get("date_Of_Birth")));
                user.setName(requestMap.get("name"));
                user.setEmail(requestMap.get("email"));
                //user.setPassword(passwordEncoder.encode(requestMap.get("password")));
                user.setPassword(optional.get().getPassword());
                user.setPhone(requestMap.get("phone"));

                // Parse updatedAt and createdAt using DateTimeFormatter.ISO_LOCAL_DATE_TIME
                try {
                    LocalDateTime updatedDateTime = LocalDateTime.parse(requestMap.get("updatedAt"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime createdDateTime = LocalDateTime.parse(requestMap.get("createdAt"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    user.setUpdatedAt(updatedDateTime);
                    user.setCreatedAt(createdDateTime);
                } catch (DateTimeParseException e) {
                    return new ResponseEntity<>("{\"message\":\"Invalid date format.\"}", HttpStatus.BAD_REQUEST);
                }

                user.setZipcode(requestMap.get("zipcode"));
                userRepository.save(user);

                return new ResponseEntity<>("{\"message\":\"User Details Updated Successfully\"}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"message\":\"User not found in the database.\"}", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("{\"message\":\"Invalid Data.\"}", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateUserPassword(Map<String, String> requestMap) {
        try {
            String email = requestMap.get("emailID");
            String currentPassword = requestMap.get("currentPassword");
            String newPassword = requestMap.get("newPassword");

            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Check if the current password matches
                if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                    // Update the password
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);

                    return new ResponseEntity<>("{\"message\":\"Password updated successfully\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Current password is incorrect\"}",
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"User not found\"}", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"message\":\"Error updating password\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateUserDetails(Map<String, String> requestMap) {
        List<String> requiredKeys = Arrays.asList(
            "address", "date_Of_Birth", "email", "name", "phone", "zipcode"
        );
    
        return requiredKeys.stream().allMatch(requestMap::containsKey);
    }  
    
    
    public ResponseEntity<String> findUserByEmail(Map<String, String> requestMap){
           try {

            var user = userRepository.findByEmail(requestMap.get("emailID"));
            if (user.isPresent()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String userJson = objectMapper.writeValueAsString(user.get());
                return new ResponseEntity<String>("{\"Success\":" + userJson + "}",
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("{\"message\":\"" + "User not found" + "\"}",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (JsonProcessingException e) {
            // Handle the exception appropriately
            return new ResponseEntity<String>("{\"message\":\"Error converting to JSON\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}