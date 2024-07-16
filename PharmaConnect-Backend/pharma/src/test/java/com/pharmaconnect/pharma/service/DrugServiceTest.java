package com.pharmaconnect.pharma.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;
import java.sql.Date;
import com.pharmaconnect.pharma.dao.DrugsDao;
import com.pharmaconnect.pharma.dao.StoreDrugPriceDao;
import com.pharmaconnect.pharma.entity.Drugs;
import com.pharmaconnect.pharma.entity.StoreDrug;
import com.pharmaconnect.pharma.entity.StoreDrugPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DrugServiceTest {


    @Mock
    DrugsDao drugsDao;

    @Mock
    StoreDrugPriceDao storeDrugPriceDao;

    @InjectMocks
    DrugServcie drugServcie;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    public Map<String, String> validData()
    {


        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("code", "123");
        requestMap.put("store_id", "1");
        requestMap.put("unit_price", "10.0");
        requestMap.put("drug_id", "2");
        requestMap.put("quantity", "100");

        return requestMap;
    }

     @Test
    void testAddNewDrug() {
        
        // Arrange
        Map<String, String> requestMap = validData();

        when(drugsDao.save(any())).thenReturn(requestMap);

        // Act
        ResponseEntity<String> response = drugServcie.addNewDrug(requestMap);

        // VAssert
        verify(storeDrugPriceDao, times(1)).save(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Drug Details Added"));
    }

    @Test
    void testAddNewDrug_InvalidData() {
        
        // Arrange add invalid data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("code", "123");
        requestMap.put("store_id", "1");

        // Act
        ResponseEntity<String> response = drugServcie.addNewDrug(requestMap);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Invalid Data."));

    }

    @Test
    void testValidateNewDrug_ValidData() {
        
        // Arrange
        Map<String, String> requestMap = validData();

        // Act
        boolean result = drugServcie.validateNewDrug(requestMap);

        // Assert
        assertTrue(result, "Valid data should return true");
    }
    
    @Test
    void testValidateNewDrug_MissingField() {

        // Arrange: test data with missing field
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("code", "123");
        requestMap.put("store_id", "1");
       
        // Act
        boolean result = drugServcie.validateNewDrug(requestMap);

        // Assert
        assertFalse(result, "Missing field should return false");
    }

    @Test
    void testValidateNewDrug_EmptyMap() {
        
        // Arrange
        Map<String, String> requestMap = new HashMap<>();

        // Act
        boolean result = drugServcie.validateNewDrug(requestMap);

        // Assert
        assertFalse(result, "Empty map should return false");
    }

    @Test
    void testGetStoreDrugPriceFromMap_ValidData() {

        // Arrange
        Map<String, String> requestMap = validData();

        // Act
        StoreDrugPrice result = drugServcie.getStoreDrugPriceFromMap(requestMap);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.getStoreDrugPriceId().getDrugId());
        assertEquals(1, result.getStoreDrugPriceId().getStoreId());
        assertEquals(10.0, result.getUnit_price(), 0.001);
        assertEquals("123", result.getCode());
        assertEquals(100, result.getQuantity());
    }

    @Test
    void testGetAllDrugs() {

        // Arrange
        Drugs drug = Drugs.builder()
                .drugId(1)
                .drug_name("TestDrug1")
                .code("A23")
                .unit_price(10.0f)
                .company_name("Company1")
                .production_date(Date.valueOf("2023-11-01"))
                .expiration_date(Date.valueOf("2023-12-31"))
                .prescription(true)
                .description("Test Drug")
                .build();


        List<Drugs> expectedDrugs = Collections.singletonList(drug);

        when(drugsDao.findAll()).thenReturn(expectedDrugs);

        // Act
        List<Drugs> result = drugServcie.getAllDrugs();

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDrugs.size(), result.size(), "Size of result should match the size of expectedDrugs");
        assertTrue(result.containsAll(expectedDrugs), "Result should contain all expectedDrugs");


        verify(drugsDao, times(1)).findAll();

    }

    @Test
    void testUpdateStoreDrugPrice_ValidData() {
        
        // Act 
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("code", "123");
        requestMap.put("store_id", "1");
        requestMap.put("unit_price", "10.0");
        requestMap.put("drug_id", "2");
        requestMap.put("quantity", "100");
    
        when(storeDrugPriceDao.findByStoreDrugPriceId(any())).thenReturn(Optional.of(new StoreDrugPrice()));

        // Act
        ResponseEntity<String> response = drugServcie.updateStoreDrugPrice(requestMap);

        // Assert
        verify(storeDrugPriceDao, times(1)).findByStoreDrugPriceId(any());
        verify(storeDrugPriceDao, times(1)).save(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Store Drug Price Details Updated"));
    }

    @Test
    void testUpdateStoreDrugPrice_DrugNotFound() {
        
        // Arrange
        Map<String, String> requestMap = validData();


        when(storeDrugPriceDao.findByStoreDrugPriceId(any())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = drugServcie.updateStoreDrugPrice(requestMap);

        // Arrange
        verify(storeDrugPriceDao, times(1)).findByStoreDrugPriceId(any());
        verify(storeDrugPriceDao, never()).save(any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Not able to found the drug in the database."));
    }

    @Test
    public void testDeleteStoreDrugPriceId() {
       
        // Arrange
        int drugId = 2;
        int storeId = 1;

        // Mock the behavior of deleteById method
        doNothing().when(storeDrugPriceDao).deleteById(new StoreDrug(drugId, storeId));

        // Act
        ResponseEntity<String> response = drugServcie.deleteStoreDrugPriceId(drugId, storeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Store Drug Price Details Deleted\"}", response.getBody());

        // Verify that deleteById method was called once
        verify(storeDrugPriceDao, times(1)).deleteById(new StoreDrug(drugId, storeId));
    }

    @Test
    public void testDeleteStoreDrugPriceId_InvalidData() {
        
        // Arrange
        int drugId = -1;
        int storeId = -1;

        // Act
        ResponseEntity<String> response = drugServcie.deleteStoreDrugPriceId(drugId, storeId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Invalid Data store or drug ID\"}", response.getBody());

        verify(storeDrugPriceDao, never()).deleteById(any());
    }

    /*@Test
    public void testDeleteStoreDrugPriceId_Exception() {
        
        // Arrange
        int drugId = 2;
        int storeId = 1;

        doThrow(new RuntimeException("Test exception")).when(storeDrugPriceDao).deleteById(new StoreDrug(drugId, storeId));

        // Act
        ResponseEntity<String> response = drugServcie.deleteStoreDrugPriceId(drugId, storeId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\":\"Something went wrong in Drug Service Implementation.\"}", response.getBody());

        verify(storeDrugPriceDao, times(1)).deleteById(new StoreDrug(drugId, storeId));
    }*/

    /*@Test
    public void testDeleteStoreDrugPrice_Exception() {
        
        // Arrange
        Map<String, String> requestMap = validData();

        doThrow(new RuntimeException("Test exception")).when(storeDrugPriceDao).save(any());

        // Act
        ResponseEntity<String> response = drugServcie.addNewDrug(requestMap);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\":\"Something went wrong in Drug Service Implementation.\"}", response.getBody());

        verify(storeDrugPriceDao, times(1)).save(any());
    }*/

    
}
