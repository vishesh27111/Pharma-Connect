package com.pharmaconnect.pharma.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userRepository;

    @Mock
    PasswordEncoder passwordEncoder ;

    @InjectMocks
    private UserService userService;


    @Test
    public void testUpdateUserPasswordSuccess() {
        // Arrange
        String email = "test@example.com";
        String currentPassword = "Hash@123";
        String newPassword = "Ram@123";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("emailID", email);
        requestMap.put("currentPassword", currentPassword);
        requestMap.put("newPassword", newPassword);

        User existingUser = new User();
        existingUser.setEmail(email);
        when(passwordEncoder.encode(currentPassword)).thenReturn("Hash@123");
        existingUser.setPassword(passwordEncoder.encode(currentPassword));

        Optional<User> userOptional = Optional.of(existingUser);
        when(userRepository.findByEmail(email)).thenReturn(userOptional);
        when(passwordEncoder.matches(currentPassword, existingUser.getPassword())).thenReturn(true);

        // Act
        ResponseEntity<String> response = userService.updateUserPassword(requestMap);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Password updated successfully\"}", response.getBody());

        // Verify that userRepository.save was called with the correct user
        verify(userRepository).save(existingUser);
    }


    @Test
    public void testUpdateUserPasswordIncorrectCurrentPassword() {

        // Arrange
        String email = "test@example.com";
        String currentPassword = "Hash@123";
        String newPassword = "Ram@123";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("emailID", email);
        requestMap.put("currentPassword", currentPassword);
        requestMap.put("newPassword", newPassword);

        User existingUser = new User();
        existingUser.setEmail(email);
        when(passwordEncoder.encode(currentPassword)).thenReturn(currentPassword);

        existingUser.setPassword(passwordEncoder.encode(currentPassword));
        Optional<User> userOptional = Optional.of(existingUser);
        when(userRepository.findByEmail(email)).thenReturn(userOptional);
        when(passwordEncoder.matches(currentPassword, existingUser.getPassword())).thenReturn(false);


        // Act
        ResponseEntity<String> response = userService.updateUserPassword(requestMap);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Current password is incorrect\"}", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUserPasswordUserNotFound() {
        // Arrange
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("emailID", "nonexistent@example.com");
        requestMap.put("currentPassword", "oldPassword");
        requestMap.put("newPassword", "newPassword");

        // Act
        ResponseEntity<String> response = userService.updateUserPassword(requestMap);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\":\"User not found\"}", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    /*@Test
    public void testErrorUpdatingPassword() {
        
        String email = "test@example.com";
        String currentPassword = "Rameez@1234";
        String newPassword = "Ram@123";

        // Arrange
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("emailID", "test@example.com");
        requestMap.put("currentPassword", "Rameez@1234");
        requestMap.put("newPassword", newPassword);

        User user = new User();
        user.setEmail("jacob.martin@gmail.com");
        user.setPassword(passwordEncoder.encode("Rameez@1234"));
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByEmail(anyString())).thenReturn(userOptional); // Assume user is found
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true); // Assume password matches
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Simulated error"));

        // Act
        ResponseEntity<String> response = userService.updateUserPassword(requestMap);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\":\"Error updating password\"}", response.getBody());
    }*/

    public void testSave() {
 
        final long SAMPLE_USER_ID = 1L;
        final String SAMPLE_USER_NAME = "testUser";
        final String SAMPLE_USER_PASSWORD = "password";
 
        // mock user
        User user = new User();
        user.setUserId(SAMPLE_USER_ID);
        user.setName(SAMPLE_USER_NAME);
        user.setPassword(SAMPLE_USER_PASSWORD);
 
        // Mock the behavior of userRepository.save()
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
 
        // Act
        User savedUser = userService.save(user);
 
        Mockito.verify(userRepository).save(user);
 
        // Assert
        assertEquals(user, savedUser);
 
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
       
        final String TEST_EMAIL = "test@example.com";

        // Arrange create mock user
        User user = new User();
        user.setEmail(TEST_EMAIL);
       
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userService.loadUserByUsername(TEST_EMAIL);

        // Verify that the repository findByEmail method was called
        Mockito.verify(userRepository).findByEmail(TEST_EMAIL);

        // Assert
        assertEquals(TEST_EMAIL, userDetails.getUsername());
        
    }

     @Test
    public void testLoadUserByUsername_UserNotFound() {
        
        // Arrange mock values
        final String NON_EXISTENT_EMAIL = "nonexistent@example.com";

        when(userRepository.findByEmail(NON_EXISTENT_EMAIL)).thenReturn(Optional.empty());

        // Call the loadUserByUsername method and expect an exception
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(NON_EXISTENT_EMAIL);
        });

        // Verify 
        Mockito.verify(userRepository).findByEmail(NON_EXISTENT_EMAIL);

    }

    @Test
    public void testUpdateUserProfile_Success() {
        
        final String TEST_EMAIL = "test@example.com";
        final String VALID_DATE_FORMAT = "2023-01-01";
        final String SAMPLE_ADDRESS = "123 Main St";
        final String SAMPLE_NAME = "John Doe";
        final String SAMPLE_PHONE = "1234567890";
        final String SAMPLE_ZIPCODE = "12345";
        final String SAMPLE_UPDATED_AT = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String SAMPLE_CREATED_AT = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String EXPECTED_RESPONSE_BODY = "{\"message\":\"User Details Updated Successfully\"}";

        // Arrange create a mock user
        User user = new User();
        user.setEmail(TEST_EMAIL);


        // sample request map with valid data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("address", SAMPLE_ADDRESS);
        requestMap.put("date_Of_Birth", VALID_DATE_FORMAT);
        requestMap.put("email", TEST_EMAIL);
        requestMap.put("name", SAMPLE_NAME);
        requestMap.put("phone", SAMPLE_PHONE);
        requestMap.put("zipcode", SAMPLE_ZIPCODE);
        requestMap.put("updatedAt", SAMPLE_UPDATED_AT);
        requestMap.put("createdAt", SAMPLE_CREATED_AT);

        // Mock repository behavior
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<String> response = userService.updateUserProfile(requestMap);

        // Verify that the repository methods were called
        Mockito.verify(userRepository).findByEmail(TEST_EMAIL);
        Mockito.verify(userRepository).save(Mockito.any(User.class));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXPECTED_RESPONSE_BODY, response.getBody());

    }

    @Test
    public void testUpdateUserProfile_InvalidDateFormat() {
        final String TEST_EMAIL = "test@example.com";
        final String VALID_DATE_FORMAT = "2023-01-01";
        final String SAMPLE_ADDRESS = "123 Main St";
        final String SAMPLE_NAME = "John Doe";
        final String SAMPLE_PHONE = "1234567890";
        final String SAMPLE_ZIPCODE = "12345";
        final String SAMPLE_VALID_UPDATED_AT = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String SAMPLE_INVALID_CREATED_AT = "InvalidDate"; // Invalid date format intentionally
        final String EXPECTED_INVALID_DATE_RESPONSE = "{\"message\":\"Invalid date format.\"}";

        // Create a mock request map with invalid data
        Map<String, String> validRequestMap = new HashMap<>();
        validRequestMap.put("address", SAMPLE_ADDRESS);
        validRequestMap.put("date_Of_Birth", VALID_DATE_FORMAT);
        validRequestMap.put("email", TEST_EMAIL);
        validRequestMap.put("name", SAMPLE_NAME);
        validRequestMap.put("phone", SAMPLE_PHONE);
        validRequestMap.put("zipcode", SAMPLE_ZIPCODE);
        validRequestMap.put("updatedAt", SAMPLE_VALID_UPDATED_AT);
        validRequestMap.put("createdAt", SAMPLE_INVALID_CREATED_AT);

        // Mock repository behavior
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<String> response = userService.updateUserProfile(validRequestMap);

        // Verify that the repository findByEmail method was called
        Mockito.verify(userRepository).findByEmail(TEST_EMAIL);

        // Assert the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EXPECTED_INVALID_DATE_RESPONSE, response.getBody());

    }

    @Test
    public void testUpdateUserProfile_InvalidData() {
        final String EXPECTED_MISSING_DATA_RESPONSE = "{\"message\":\"Invalid Data.\"}";

        // Create a mock request map with missing data/ emply dataset
        Map<String, String> requestMap = new HashMap<>();
       

        // Call the updateUserProfile method
        ResponseEntity<String> response = userService.updateUserProfile(requestMap);

        // Verify that the method returns the expected result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EXPECTED_MISSING_DATA_RESPONSE, response.getBody());

    }

    @Test
    public void testUpdateUserProfile_UserNotFound() {

        final String NON_EXISTENT_EMAIL = "nonexistent@example.com";
        final String EXPECTED_USER_NOT_FOUND_RESPONSE = "{\"message\":\"User not found in the database.\"}";

        final String VALID_DATE_FORMAT = "2023-01-01";
        final String SAMPLE_ADDRESS = "123 Main St";
        final String SAMPLE_NAME = "John Doe";
        final String SAMPLE_PHONE = "1234567890";
        final String SAMPLE_ZIPCODE = "12345";
        final String SAMPLE_VALID_UPDATED_AT = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String SAMPLE_INVALID_CREATED_AT = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Create a mock request map with valid data
        Map<String, String> validRequestMap = new HashMap<>();
        validRequestMap.put("address", SAMPLE_ADDRESS);
        validRequestMap.put("date_Of_Birth", VALID_DATE_FORMAT);
        validRequestMap.put("email", NON_EXISTENT_EMAIL);
        validRequestMap.put("name", SAMPLE_NAME);
        validRequestMap.put("phone", SAMPLE_PHONE);
        validRequestMap.put("zipcode", SAMPLE_ZIPCODE);
        validRequestMap.put("updatedAt", SAMPLE_VALID_UPDATED_AT);
        validRequestMap.put("createdAt", SAMPLE_INVALID_CREATED_AT);

        // Mock repository behavior for a user not found scenario
        when(userRepository.findByEmail(NON_EXISTENT_EMAIL)).thenReturn(Optional.empty());

        // Call the method
        ResponseEntity<String> response = userService.updateUserProfile( validRequestMap);

        // Verify that the repository findByEmail method was called
        Mockito.verify(userRepository).findByEmail(NON_EXISTENT_EMAIL);

        // Verify the response 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EXPECTED_USER_NOT_FOUND_RESPONSE, response.getBody());
        
    }


}
