package com.pharmaconnect.pharma.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.pharmaconnect.pharma.entity.Reservation;


@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {
   Reservation findByReservationId(Integer reservationId);
   List<Reservation> findByUserEmail(String userEmail);
   List<Reservation> findByStoreId(Long storeId);

   @Query(value = "SELECT * FROM RESERVATION WHERE status = 'Waiting'", nativeQuery = true)
   List<Reservation> findReservationsToRelease();

}
