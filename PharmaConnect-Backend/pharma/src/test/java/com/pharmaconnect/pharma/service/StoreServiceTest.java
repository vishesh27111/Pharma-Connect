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

import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.entity.Store;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreDao storeRepository;

    @Mock
    PasswordEncoder passwordEncoder ;

    @InjectMocks
    private StoreService storeService;


    @Test
    public void testUpdateStorePasswordSuccess() {

        Long storeId=1L;
        String currentPassword = "Hash@123";
        // Mock data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("currentPassword", "Hash@123");
        requestMap.put("newPassword", "Ram@123");

        Store existingStore = new Store();
        existingStore.setStoreId(1L);
        when(passwordEncoder.encode(currentPassword)).thenReturn("Hash@123");
        existingStore.setPassword(passwordEncoder.encode(currentPassword));

        Optional<Store> storeOptional = Optional.of(existingStore);
        when(storeRepository.findById(storeId)).thenReturn(storeOptional);
        when(passwordEncoder.matches(currentPassword, existingStore.getPassword())).thenReturn(true);

        // Call the method
        ResponseEntity<String> response = storeService.updateStorePassword(requestMap);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Store Password Updated Successfully\"}", response.getBody());
        verify(storeRepository, times(1)).save(existingStore);
    }


    @Test
    public void testUpdateStorePasswordIncorrect() {

        Long storeId=1L;
        String currentPassword = "Hash@123";
        // Mock data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("currentPassword", "Hash@123");
        requestMap.put("newPassword", "Ram@123");

        Store existingStore = new Store();
        existingStore.setStoreId(1L);
        when(passwordEncoder.encode(currentPassword)).thenReturn("Hash@123");
        existingStore.setPassword(passwordEncoder.encode(currentPassword));

        Optional<Store> storeOptional = Optional.of(existingStore);
        when(storeRepository.findById(storeId)).thenReturn(storeOptional);
        when(passwordEncoder.matches(currentPassword, existingStore.getPassword())).thenReturn(false);

        // Call the method
        ResponseEntity<String> response = storeService.updateStorePassword(requestMap);

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Current password is incorrect\"}", response.getBody());

    }


    @Test
     public void testUpdateStorePasswordStoreNotFound() {
        // Mock data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("currentPassword", "Hash@123");
        requestMap.put("newPassword", "Ram@123");


        // Call the method
        ResponseEntity<String> response = storeService.updateStorePassword(requestMap);

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       assertEquals("{\"message\":\"Store not found in the database.\"}", response.getBody());
    }


    @Test
    public void testUpdateStorePasswordInvalidData() {
        // Mock data
        Map<String, String> requestMap = new HashMap<>();

        // Call the method
        ResponseEntity<String> response = storeService.updateStorePassword(requestMap);

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Invalid Data.\"}", response.getBody());
        verify(storeRepository, never()).save(any());
    }

    @Test
    public void testValidateStorePasswordDetails() {
        // Mock data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("currentPassword", "oldPassword");
        requestMap.put("newPassword", "newPassword");

        // Call the method
        boolean result = storeService.validateStorePasswordDetails(requestMap);

        // Verify the results
        assertEquals(true, result);
    }

    @Test
    public void testValidateStorePasswordDetailsInvalidData() {
        // Mock data
        Map<String, String> requestMap = new HashMap<>();

        // Call the method
        boolean result = storeService.validateStorePasswordDetails(requestMap);

        // Verify the results
        assertEquals(false, result);
    }
    
     @Test
     public void testSaveStore_NewStore() {
        
        // Create a mock store for testing without a storeId
        String storeName = "Test Store";
        Store newStore = new Store();
        newStore.setStore_name(storeName);

        // Mock repository behavior
        when(storeRepository.save(any(Store.class))).thenReturn(newStore);

        // Call the save method
        Store savedStore = storeService.save(newStore);

        // Verify that the repository save method was called
        Mockito.verify(storeRepository).save(newStore);

        // Assert the result
       assertEquals(newStore,savedStore);

    }

    @Test
    public void testSaveStore_ExistingStore() {
        
        // Create a mock store for testing 
        Store existingStore = new Store();
        existingStore.setStoreId(1L);
        existingStore.setStore_name("Existing Store");

        // Mock repository behavior
        when(storeRepository.save(any(Store.class))).thenReturn(existingStore);

        // Call the save method
        Store savedStore = storeService.save(existingStore);

        // Verify that the repository save method was called
        Mockito.verify(storeRepository).save(existingStore);

        // Verify the saved result
        assertEquals(existingStore,savedStore);
    }

     @Test
     public void testLoadUserByUsername_StoreFound() {

        String storeEmail = "TestStore@gmail.com";

        // Create a mock store by storeEmail 
        Store store = new Store();
        store.setEmail(storeEmail);
    
        // Mock repository behavior
        when(storeRepository.findByEmail(storeEmail)).thenReturn(Optional.of(store));

        // Call the loadUserByUsername method
        UserDetails userDetails = storeService.loadUserByUsername(storeEmail);

        // Verify that the repository findByEmail method was called
        Mockito.verify(storeRepository).findByEmail(storeEmail);

        // Verify tht results are corrects
        assertEquals(storeEmail, userDetails.getUsername());
        
    }

    @Test
    public void testLoadUserByUsername_StoreNotFound() {
        
        // Mock repository behavior 
        String storeName = "Testnonexistingstore@gmail.com";

        when(storeRepository.findByEmail(storeName)).thenReturn(Optional.empty());

        // Throw an exception
        assertThrows(UsernameNotFoundException.class, () -> {
            storeService.loadUserByUsername(storeName);
        });

        // Verify that the repository findByEmail method was called
        Mockito.verify(storeRepository).findByEmail(storeName);
    }

    @Test
    public void testUpdateStoreProfile_ValidData() {
        
        // Create a mock request map with valid data
        Map<String, String> requestMap = ValidDataSet();

        Store store = new Store();
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));

        // Mock repository behavior for an existing store
        when(storeRepository.save(any())).thenReturn(store);

        ResponseEntity<String> response = storeService.updateStoreProfile(requestMap);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Store Details Updated Successfully\"}", response.getBody());

        when(storeRepository.findById(anyLong())).thenReturn(Optional.empty());
        response = storeService.updateStoreProfile(requestMap);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Store not found in the database.\"}", response.getBody());

        // Test the case where validation fails

        response = storeService.updateStoreProfile(InValidDataSet());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Invalid Data.\"}", response.getBody());
    }

    @Test
    public void testValidateStoreDetails_ValidData() {
       
        // Create a mock valid request map
        Map<String, String> requestMap = ValidDataSet();

        // Call the validateStoreDetails method
        boolean result = storeService.validateStoreDetails(requestMap);

        // Verify that the result is true 
        assertTrue(result);
    }

    @Test
    public void testValidateStoreDetails_MissingData() {
       
        // Create a mock request map with missing data
        Map<String, String> requestMap = InValidDataSet();

        // Call the validateStoreDetails method
        boolean result = storeService.validateStoreDetails(requestMap);

        // Verify that the result is false for missing data
        assertFalse(result);

    }

    @Test
    public void testUpdateStoreProfile_InvalidDate() {

        final String EXPECTED_INVALID_DATE_RESPONSE = "{\"message\":\"Invalid date format.\"}";
        
        // Create a mock request map with incorrect date
        Map<String, String> requestMap = InValidDateDataSet();

        Store store = new Store();
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));

         // Call the method to update details
        ResponseEntity<String> response = storeService.updateStoreProfile(requestMap);

        // Verify the expected result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EXPECTED_INVALID_DATE_RESPONSE, response.getBody());

    }

    // valid data request dataset
    private Map<String, String> ValidDataSet() {

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("email", "store@example.com");
        requestMap.put("address", "123 Main St");
        requestMap.put("manager_name", "John Doe");
        requestMap.put("password", "secretpassword");
        requestMap.put("phone", "1234567890");
        requestMap.put("registration_number", "12345");
        requestMap.put("store_name", "My Store");
        requestMap.put("updatedAt", "2023-01-01T12:00:00");
        requestMap.put("monday", "open");
        requestMap.put("tuesday", "closed");
        requestMap.put("wednesday", "open");
        requestMap.put("thursday", "closed");
        requestMap.put("friday", "open");
        requestMap.put("saturday", "closed");
        requestMap.put("sunday", "open");

        return requestMap;
    }

    /* Invalid/missing request dataset */
    private Map<String, String> InValidDataSet() {

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("email", "store@example.com");


        return requestMap;
    }

    // Invalid Date format
    private Map<String, String> InValidDateDataSet() {

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("storeId", "1");
        requestMap.put("email", "store@example.com");
        requestMap.put("address", "123 Main St");
        requestMap.put("manager_name", "John Doe");
        requestMap.put("password", "secretpassword");
        requestMap.put("phone", "1234567890");
        requestMap.put("registration_number", "12345");
        requestMap.put("store_name", "My Store");
        requestMap.put("updatedAt", "InvalidDate");
        requestMap.put("monday", "open");
        requestMap.put("tuesday", "closed");
        requestMap.put("wednesday", "open");
        requestMap.put("thursday", "closed");
        requestMap.put("friday", "open");
        requestMap.put("saturday", "closed");
        requestMap.put("sunday", "open");

        return requestMap;
    }

}
