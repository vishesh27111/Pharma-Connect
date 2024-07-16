package com.pharmaconnect.pharma.service;


import com.pharmaconnect.pharma.dao.StoreLocationDao;
import com.pharmaconnect.pharma.entity.StoreLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
public class StoreLocationServiceTest {
    
    @Mock
    private StoreLocationDao storeLocationDao;
 
    @Mock
    StoreLocation storeLocation;
 
    @InjectMocks
    StoreLocationService storeLocationService;
 
    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLocations() {
        // Test data
        Integer location_id = 1;
        Integer store_id = 1;
        String store_name = "MockStore1";
        String store_number = "12345";
        String address = "Mock Address 1";
        String city = "MockCity1";
        String state = "MockState1";
        String country = "MockCountry1";
        String postcode = "123456";
        BigDecimal latitude = new BigDecimal("40.7128");
        BigDecimal longitude = new BigDecimal("-74.0060");
 
        // Mocking the behavior of storeLocationDao
        storeLocation.setLocation_id(location_id);
        List<StoreLocation> mockLocations = new ArrayList<>();
        StoreLocation location1 = new StoreLocation();
 
        // Set up mock data
        location1.setLocation_id(location_id);
        location1.setStore_id(store_id);
        location1.setStore_name(store_name);
        location1.setStore_number(store_number);
        location1.setAddress(address);
        location1.setCity(city);
        location1.setState(state);
        location1.setCountry(country);
        location1.setPostcode(postcode);
        location1.setLatitude(latitude); // Mock latitude value
        location1.setLongitude(longitude);
 
        mockLocations.add(location1);
 
        // Mock the behavior of storeLocationDao.findAll()
        when(storeLocationDao.findAll()).thenReturn(mockLocations);
 
        // Calling the method to be tested
        List<StoreLocation> result = storeLocationService.getAllLocations();
 
        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
    }
 
    /*@Test
    void testGetAllLocationsException() {
        // Mock an exception when storeLocationDao.findAll() is called
        when(storeLocationDao.findAll()).thenThrow(new MockException("Mocked exception"));
 
        try {
            // Calling the method to be tested
            List<StoreLocation> result = storeLocationService.getAllLocations();
 
            // Assertions after the exception is thrown (optional)
            assertNull(result);
        } catch (MockException exception) {
            // Assertions on the caught exception
            assertEquals("Mocked exception", exception.getMessage());
        }
    }*/
 
    // Custom exception for mocking purposes
    static class MockException extends RuntimeException {
        public MockException(String message) {
            super(message);
        }
    }

}
