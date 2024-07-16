package com.pharmaconnect.pharma.service;

import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.entity.User;
import com.pharmaconnect.pharma.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserService userService;

    @Mock
    private StoreDao storeDao;

    @Mock
    private StoreService storeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUpUser_Success() {

        String name = "Test user";
        String email = "testuser@gmail.com";
        String password = "testpassword";
        Date dateOfBirth = new Date(System.currentTimeMillis()); // Replace with an actual date
        String phone = "1234567890";
        String address = "south Street";
        String zipcode = "12345";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // Arrange
        SignUpUserRequest request = new SignUpUserRequest();
        request.setEmail(email);
        request.setAddress(address);
        request.setPassword(password);
        request.setName(name);
        request.setPhone(phone);
        request.setDate_Of_Birth(dateOfBirth);
        request.setCreatedAt(createdAt);
        request.setUpdatedAt(updatedAt);
        request.setZipcode(zipcode);

        // Set other required fields in the request

        when(userDao.findByEmail(any())).thenReturn(Optional.empty());
        when(userService.save(any())).thenReturn(new User());

        // Act
        ResponseEntity<JwtAuthenticationResponseUser> response = authenticationService.signUpUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testSignInUser_Success() {

        String mockToken = "mockedJwt";
        String email = "testuser@gmail.com";
        String password = "password123";
        // Arrange
        SignInUserRequest request = new SignInUserRequest();
        request.setEmail(email);
        request.setPassword(password);

        User mockedUser = new User(); // Create a mocked User object
        when(userDao.findByEmail(any())).thenReturn(Optional.of(mockedUser));
        when(jwtService.generateToken(any())).thenReturn(mockToken);

        // Act
        JwtAuthenticationResponseUser response = authenticationService.signInUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockToken, response.getToken());
    }

    @Test
    void testSignInUser_InvalidCredentials() {

        // Arrange
        String email = "testinvaliduser@gmail.com";
        String password = "invalidpassword123";
        SignInUserRequest request = new SignInUserRequest();
        request.setEmail(email);
        request.setPassword(password);

        when(userDao.findByEmail(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> authenticationService.signInUser(request));
    }

    @Test
    void testSignUpStore_Success() {

        // Arrange
        String StoreName = "Test Store";
        String mockToken = "mockedJwt";
        SignUpStoreRequest request = new SignUpStoreRequest();
        request.setStore_name(StoreName);

        // Mock store
        Store mockedStore = new Store();
        when(storeService.save(any())).thenReturn(mockedStore);
        when(jwtService.generateToken(any())).thenReturn(mockToken);

        // Act
        JwtAuthenticationResponseStore response = authenticationService.signUpStore(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockToken, response.getToken());
    }

    @Test
    void testSignInStore_Success() {
        // Arrange
        String email = "testuser@gmail.com";
        String password = "password";
        String mockToken = "mockedJwt";
        SignInStoreRequest request = new SignInStoreRequest();
        request.setEmail(email);
        request.setPassword(password);

        Store mockedStore = new Store(); // Create a mocked Store object
        when(storeDao.findByEmail(any())).thenReturn(Optional.of(mockedStore));
        when(jwtService.generateToken(any())).thenReturn(mockToken);

        // Act
        JwtAuthenticationResponseStore response = authenticationService.signInStore(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockToken, response.getToken());

    }

    @Test
    void testSignInStore_InvalidCredentials() {
        // Arrange
        String email = "testinvalidstore@gmail.com";
        String password = "invalidpassword";
        SignInStoreRequest request = new SignInStoreRequest();
        request.setEmail(email);
        request.setPassword(password);

        when(storeDao.findByEmail(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> authenticationService.signInStore(request));
    }

    @Test
    void testCreateBadRequestResponse() {
        // Arrange
        String errorMessage = "Bad request";
        AuthenticationService authenticationService = new AuthenticationService();

        // Act
        ResponseEntity<JwtAuthenticationResponseUser> responseEntity = authenticationService.createBadRequestResponse(errorMessage);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        JwtAuthenticationResponseUser responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertNull(responseBody.getToken());
        assertEquals(errorMessage, responseBody.getErrorMessage());
    }

    @Test
    void testCreateExpectationFailedResponse() {
        // Arrange
        String errorMessage = "Test expectation failed";
        AuthenticationService authenticationService = new AuthenticationService();

        // Act
        ResponseEntity<JwtAuthenticationResponseUser> responseEntity = authenticationService.createExpectationFailedResponse(errorMessage);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());

        JwtAuthenticationResponseUser responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertNull(responseBody.getToken());
        assertEquals(errorMessage, responseBody.getErrorMessage());
    }

}
