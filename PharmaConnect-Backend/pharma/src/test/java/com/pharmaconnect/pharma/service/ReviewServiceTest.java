package com.pharmaconnect.pharma.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaconnect.pharma.dao.ReviewsDao;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.Reviews;
import com.pharmaconnect.pharma.entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewsService reviewsService;

    @Mock
    private ReviewsDao reviewsDao;

    @Mock
    private UserDao userRepository;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStoreReviews() {
       
       // Arrange
       int storeId = 1;
       int reviewId = 1;
       String storeName = "Test Review Store";
       int rating = 5;
       Date reviewDate = Date.valueOf("2023-11-11");
       String message = "Test reviews";
       Long userId = 1L;
       
       Reviews mockReview = new Reviews();
       mockReview.setReviewId(reviewId);
       mockReview.setStoreId(storeId);
       mockReview.setStoreName(storeName);
       mockReview.setRating(rating);
       mockReview.setReviewDate(reviewDate);
       mockReview.setMessage(message);
       mockReview.setUserId(userId);

        List<Reviews> mockReviewsList = new ArrayList<>();
        mockReviewsList.add(mockReview);

        when(reviewsDao.findByStore(storeId)).thenReturn(mockReviewsList);

        User existingUser = new User();
        existingUser.setName("John");
        Optional<User> userOptional = Optional.of(existingUser);
        when(userRepository.findById(mockReview.getUserId())).thenReturn(userOptional);

        // ObjectMapper objectMapper=new ObjectMapper();
        // when(objectMapper.writeValueAsString(mockReviewsList)).thenReturn(message);


        // Act
        ResponseEntity<String> result = reviewsService.getStoreReviews(storeId);

        //Assert
        verify(reviewsDao).findByStore(storeId);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    public void testAddStoreReviews() {
        
        // Assert
        int storeId = 1;
        int reviewId = 1;
        String storeName = "Test Review Store";
        int rating = 5;
        Date reviewDate = Date.valueOf("2023-11-11");
        String message = "Test reviews";
        Long userId = 1L;

        Reviews mockReview = new Reviews();
        mockReview.setReviewId(reviewId);
        mockReview.setStoreId(storeId);
        mockReview.setStoreName(storeName);
        mockReview.setRating(rating);
        mockReview.setReviewDate(reviewDate);
        mockReview.setMessage(message);
        mockReview.setUserId(userId);

        
        when(reviewsDao.save(mockReview)).thenReturn(mockReview);

        // Act
        Reviews result = reviewsService.addStoReviews(mockReview);

        // Assert
        verify(reviewsDao).save(mockReview);
        assertEquals(mockReview, result);
    }

   /* @Test
    public void testAddStoreReviewsWithException() {
        // Create a mock review
        int storeId=1;
        Reviews mockReview = new Reviews();

        // Mock the behavior of the reviewsDao.save method to throw an exception
        when(reviewsDao.save(mockReview)).thenThrow(new RuntimeException("Add Exception Check"));
        when(reviewsDao.findByStore(storeId)).thenThrow(new RuntimeException("Get Store Exception Check"));

        // Call the service method
        Reviews addResult = reviewsService.addStoReviews(mockReview);
        List<Reviews> getResult = reviewsService.getStoreReviews(storeId);

        // Verify that the method was called with the correct parameters
        verify(reviewsDao).save(mockReview);

        // Verify that the result is null when an exception is caught
        assertNull(addResult);
        assertNull(getResult);
    }*/ 


}
