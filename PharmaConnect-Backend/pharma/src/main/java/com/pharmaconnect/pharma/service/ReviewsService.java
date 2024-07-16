package com.pharmaconnect.pharma.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharmaconnect.pharma.dao.ReviewsDao;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.Reviews;
import com.pharmaconnect.pharma.entity.User;
import com.pharmaconnect.pharma.model.ReviewUserResponse;

@Service
public class ReviewsService {

    protected Logger log = LoggerFactory.getLogger(ReviewsService.class);

    @Autowired
    ReviewsDao reviewsDao;

    @Autowired
    UserDao userRepository;

    public ResponseEntity<String> getStoreReviews(int storeId) {
        log.info("------------getStoreReviews called---------");
        try {
            List<Reviews> reviews = new ArrayList<Reviews>();
            List<ReviewUserResponse> userReviews = new ArrayList<ReviewUserResponse>();
            reviews = reviewsDao.findByStore(storeId);
            for (Reviews review : reviews) {
                ReviewUserResponse userResponse = new ReviewUserResponse();
                String userName = "";
                Optional<User> userOptional = userRepository.findById(review.getUserId());
                if (userOptional.isPresent()) {
                    userName = userOptional.get().getName();
                    userResponse.setMessage(review.getMessage());
                    userResponse.setRating(review.getRating());
                    userResponse.setReviewDate(review.getReviewDate());
                    userResponse.setStoreId(review.getStoreId());
                    userResponse.setStoreName(review.getStoreName());
                    userResponse.setUserName(userName);
                    userResponse.setReviewId(review.getReviewId());
                    userResponse.setUserId(review.getUserId());
                    userReviews.add(userResponse);
                }

            }
            String reviewDetails = "";
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            reviewDetails = objectMapper.writeValueAsString(userReviews);

            return new ResponseEntity<String>(
                    reviewDetails,
                    HttpStatus.OK);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public Reviews addStoReviews(Reviews reviews) {

        log.info("----------addStoReviews called------");

        try {
            return reviewsDao.save(reviews);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
