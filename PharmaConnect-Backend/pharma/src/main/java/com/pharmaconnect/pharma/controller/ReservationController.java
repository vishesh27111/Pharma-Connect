package com.pharmaconnect.pharma.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pharmaconnect.pharma.service.ReservationService;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/user/drug-reserve")
    public ResponseEntity<String> reserveMedicine(@RequestBody(required = true) Map<String, String> requestMap) {
        return reservationService.reserveMedicine(requestMap);
    }

    @PostMapping("/store/drug-purchase")
    public ResponseEntity<String> purchaseMedicine(@RequestBody(required = true) Map<String, String> requestMap) {
        return reservationService.purchaseMedicine(requestMap);
    }

    @GetMapping(path = "/user/reserved-bookings/{userEmail}")
    public ResponseEntity<String> findUserReservations(@PathVariable String userEmail) {
        return reservationService.findUserReservations(userEmail);
    }

    @GetMapping(path = "/store/reserved-bookings/{storeId}")
    public ResponseEntity<String> findStoreReservations(@PathVariable Long storeId) {
        return reservationService.findStoreReservations(storeId);
    }

}