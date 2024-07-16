package com.pharmaconnect.pharma.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharmaconnect.pharma.dao.DrugsDao;
import com.pharmaconnect.pharma.dao.ReservationDao;
import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.dao.StoreDrugPriceDao;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.Drugs;
import com.pharmaconnect.pharma.entity.Reservation;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.entity.StoreDrug;
import com.pharmaconnect.pharma.entity.StoreDrugPrice;
import com.pharmaconnect.pharma.entity.User;
import com.pharmaconnect.pharma.model.ReservationEmailDetails;
import com.pharmaconnect.pharma.model.StoreReservationDetails;
import com.pharmaconnect.pharma.model.UserReservationDetails;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Component
public class ReservationService {

    protected Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationDao reservationRepository;

    @Autowired
    private StoreDrugPriceDao storeDrugPriceDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private DrugsDao drugsDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Value("${mail.username}")
    private String sender;

    private ReservationEmailDetails reservationEmailDetails;
    protected Integer reservationId;
    private static final long FIXED_DELAY_ONE_MINUTE = 60000;

    /**
     * @param requestMap - It contains the Reservation Details received from the
     *                   client
     * @return - It returns the confirmation whether the medicine is reserved or not
     */


    public ResponseEntity<String> reserveMedicine(Map<String, String> requestMap) {
        log.info("-----Reserve Medicine called-----");
        Reservation reservation = new Reservation();
        reservationEmailDetails = new ReservationEmailDetails();
        Integer drug_id = Integer.parseInt(requestMap.get("drug_id"));
        Integer store_id = Integer.parseInt(requestMap.get("store_id"));
        StoreDrug storeDrug = new StoreDrug(drug_id, store_id);
        Optional<StoreDrugPrice> optional = storeDrugPriceDao.findByStoreDrugPriceId(storeDrug);
        if (!optional.isEmpty()) {
            StoreDrugPrice storeDrugPrice = optional.get();
            if (storeDrugPrice.getQuantity() >= Integer.parseInt(requestMap.get("quantity"))) {
                setReservation(requestMap, reservation, storeDrugPrice);
                Optional<User> userDetails = userRepository.findByEmail(requestMap.get("user_email"));
                if (userDetails.isPresent()) {
                    setReservationEmailDetails(requestMap, userDetails, storeDrugPrice, reservationId);
                    storeDrugPriceDao.save(storeDrugPrice);

                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "User not found" + "\"}",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }

                sendReservationMail(reservationEmailDetails);
                checkLocksAndRelease();
                return new ResponseEntity<String>("{\"message\":\"" + "Medicine is Reserved Successfully" + "\"}",
                        HttpStatus.OK);

            } else {
                String errorMessage = "{\"message\":\"" + "Quantity requested exceeds the available quantity." + "\"}";
                return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<String>(
                    "{\"message\":\"" + "No drug found." + "\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void setReservation(Map<String, String> requestMap, Reservation reservation,
            StoreDrugPrice storeDrugPrice) {
        reservation.setStoreId(Long.parseLong(requestMap.get("store_id")));
        reservation.setDrugId(Integer.parseInt(requestMap.get("drug_id")));
        reservation.setUserEmail(requestMap.get("user_email"));
        reservation.setStatus("Waiting");
        reservation.setLockAcquiredTime(LocalDateTime.now());
        reservation.setQuantityNeeded(Integer.parseInt(requestMap.get("quantity")));
        LocalDateTime lockReleaseTime = LocalDateTime.now()
                .plusMinutes(Integer.parseInt(requestMap.get("lockAcquiredTime")));
        reservation.setReleaseTime(lockReleaseTime);
        reservation.setUnitPrice(storeDrugPrice.getUnit_price());
        reservationRepository.save(reservation);
        reservationId = reservation.getReservationId();
        int neededQuantity = Integer.parseInt(requestMap.get("quantity"));
        int remainingQuantity = storeDrugPrice.getQuantity() - neededQuantity;
        storeDrugPrice.setQuantity(Math.max(0, remainingQuantity));

    }

    private void setReservationEmailDetails(Map<String, String> requestMap, Optional<User> userDetails,
            StoreDrugPrice storeDrugPrice, Integer reservationId) {
        reservationEmailDetails.setCustomerName(userDetails.get().getName());
        reservationEmailDetails.setUserEmail(userDetails.get().getEmail());
        Drugs drugs = drugsDao.findByDrugId(Integer.parseInt(requestMap.get("drug_id")));
        Store storeDetails = storeDao.findByStoreId(Long.parseLong(requestMap.get("store_id")));
        Float totalPrice = storeDrugPrice.getUnit_price() * Integer.parseInt(requestMap.get("quantity"));
        reservationEmailDetails.setMedicineName(drugs.getDrug_name());
        reservationEmailDetails.setQuantity(requestMap.get("quantity"));
        reservationEmailDetails.setReservationTime(requestMap.get("lockAcquiredTime"));
        reservationEmailDetails.setStoreName(storeDetails.getStore_name());
        reservationEmailDetails.setStoreAddress(storeDetails.getAddress());
        reservationEmailDetails.setTotalPrice(totalPrice);
        reservationEmailDetails.setDate(LocalDateTime.now());
        reservationEmailDetails.setReservationId(reservationId);
    }

    // Run every minute, adjust as needed
    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(fixedDelay = FIXED_DELAY_ONE_MINUTE)
    public void checkLocksAndRelease() {
        sessionFactory.getCache().evictAllRegions();
        List<Reservation> reservationsToRelease = reservationRepository.findReservationsToRelease();
        log.info("---\nReservationsToRelease::::" + reservationsToRelease);
        for (Reservation reservation : reservationsToRelease) {
            LocalDateTime releaseTime = reservation.getReleaseTime();
            int comparisonResult = LocalDateTime.now().compareTo(releaseTime);
            log.info("-----\ncurrentTime::::" + LocalDateTime.now());
            log.info("-----\nreleaseTime::::: " + reservation.getReleaseTime());
            log.info("------\ncomparisonResult::::" + comparisonResult);
            if (comparisonResult > 0) {
                log.info("-----\nReservation Status::::" + reservation.getStatus());
                // Check if the status is not "Purchase" before releasing the lock
                if (!"Purchase".equals(reservation.getStatus()) && !"Release".equals(reservation.getStatus())) {
                    log.info("Inside if loop ,Reservation Status::::" + reservation.getStatus());
                    // Release the lock by updating the status to "Release"
                    reservation.setStatus("Release");
                    reservationRepository.save(reservation);
                    Integer store_id = reservation.getStoreId().intValue();
                    Integer drug_id = reservation.getDrugId();
                    StoreDrug storeDrug = new StoreDrug(drug_id, store_id);
                    Optional<StoreDrugPrice> optional = storeDrugPriceDao.findByStoreDrugPriceId(storeDrug);
                    if (optional.isPresent()) {
                        StoreDrugPrice updatedValues = optional.get();
                        updatedValues.setQuantity(updatedValues.getQuantity() + reservation.getQuantityNeeded());
                        storeDrugPriceDao.save(updatedValues);
                    }
                }
            }
        }
    }

    /**
     * @param requestMap - It contains the Purchase Medicine Details from the client
     * @return - It returns the confirmation whether the medicine is purchased or
     *         not by the customer
     */
    public ResponseEntity<String> purchaseMedicine(Map<String, String> requestMap) {
         log.info("-----Purchase Medicine called-----");

        Integer reservationId = Integer.parseInt(requestMap.get("reservationId"));
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        try {
            reservationOptional.ifPresent(reservation -> {
                reservation.setStatus("Purchase");
                reservationRepository.save(reservation);

            });

        } catch (Exception e) {
            return new ResponseEntity<String>(
                    "{\"message\":\"" + "Error in saving Data." + "\"}", HttpStatus.OK);
        }
        return new ResponseEntity<String>(
                "{\"message\":\"" + "Medicine is purchased Successfully." + "\"}", HttpStatus.OK);

    }

    public String sendReservationMail(ReservationEmailDetails reservationEmailDetails) {
        try {

             log.info("-----send Reservation Email Details called-----");
            // Read HTML template
            String template = readHtmlTemplate("medicine-reservation-template.html");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the LocalDateTime using the formatter
            String formattedDateTime = reservationEmailDetails.getDate().format(formatter);

            // Replace placeholders with actual data
            StringBuilder emailBodyBuilder = new StringBuilder(template);

            // Replace placeholders in the template
            replacePlaceholder(emailBodyBuilder, "[CustomerName]", reservationEmailDetails.getCustomerName());
            replacePlaceholder(emailBodyBuilder, "[MedicineName]", reservationEmailDetails.getMedicineName());
            replacePlaceholder(emailBodyBuilder, "[Quantity]", reservationEmailDetails.getQuantity());
            replacePlaceholder(emailBodyBuilder, "[ReservationTime]", reservationEmailDetails.getReservationTime());
            replacePlaceholder(emailBodyBuilder, "[StoreName]", reservationEmailDetails.getStoreName());
            replacePlaceholder(emailBodyBuilder, "[StoreAddress]", reservationEmailDetails.getStoreAddress());
            replacePlaceholder(emailBodyBuilder, "[TotalPrice]", reservationEmailDetails.getTotalPrice().toString());
            replacePlaceholder(emailBodyBuilder, "[Date]", formattedDateTime);
            replacePlaceholder(emailBodyBuilder, "[ReservationID]", reservationEmailDetails.getReservationId().toString());

            // Get the final email body
            String emailBody = emailBodyBuilder.toString();

            // Create a MIME message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Using MimeMessageHelper to set the content and details
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(reservationEmailDetails.getUserEmail());
            helper.setSubject("Medicine Reservation Confirmation");
            helper.setText(emailBody, true); // true indicates HTML content

            // Sending the mail
            javaMailSender.send(mimeMessage);

            return "Mail Sent Successfully...";
        } catch (MessagingException | IOException e) {
            return "Error while Sending Mail";
        }
    }

    private void replacePlaceholder(StringBuilder builder, String placeholder, String value) {
        int index = builder.indexOf(placeholder);
        while (index != -1) {
            builder.replace(index, index + placeholder.length(), value);
            index = builder.indexOf(placeholder);
        }
    }

    private String readHtmlTemplate(String templatePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(templatePath);
        byte[] templateBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(templateBytes, StandardCharsets.UTF_8);
    }

    /**
     * @param userEmail - Email of the customer
     * @return - It returns all the reservations done by the customer
     */

    public ResponseEntity<String> findUserReservations(String userEmail) {

        log.info("-----find User Reservation called-----");
        String userName = null;
        // find the customer details
        Optional<User> userDetails = userRepository.findByEmail(userEmail);
        if (userDetails.isPresent()) {
            userName = userDetails.get().getName();
        }
        List<UserReservationDetails> userReservationDetails = new ArrayList<UserReservationDetails>();
        List<Reservation> userReservations = new ArrayList<Reservation>();
        userReservations = reservationRepository.findByUserEmail(userEmail);
        if (!userReservations.isEmpty()) {
            // find all the reservation for the customer
            for (Reservation userReservation : userReservations) {
                UserReservationDetails userObject = new UserReservationDetails();
                setUserReservationDetails(userReservation, userObject);
                userReservationDetails.add(userObject);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            String userReservationsJson;
            try {
                userReservationsJson = objectMapper.writeValueAsString(userReservationDetails);

            } catch (JsonProcessingException e) {
                return new ResponseEntity<String>(
                        "{\"message\":\"" + "JSON Parsing Error."
                                + "\"}",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String response = "[{\"userReservationDetails\":" + userReservationsJson
                    + ",\"userName\":" + "\"" + userName + "\"}" + "]";

            return new ResponseEntity<String>(response, HttpStatus.OK);

        } else {
            return new ResponseEntity<String>(
                    "{\"message\":\"" + "No reservations found."
                            + "\"}",
                    HttpStatus.OK);
        }
    }

    private void setUserReservationDetails(Reservation userReservation, UserReservationDetails userObject) {
        Drugs drugDetails = drugsDao.findByDrugId(userReservation.getDrugId());
        Store store = storeDao.findByStoreId(userReservation.getStoreId());
        String storeName = store.getStore_name();
        String drugName = drugDetails.getDrug_name();
        Float unitPrice = userReservation.getUnitPrice();
        userObject.setUnitPrice(unitPrice);
        userObject.setDrugName(drugName);
        userObject.setStoreName(storeName);
        userObject.setDrugId(userReservation.getDrugId());
        userObject.setLockAcquiredTime(userReservation.getLockAcquiredTime());
        userObject.setQuantityNeeded(userReservation.getQuantityNeeded());
        userObject.setReleaseTime(userReservation.getReleaseTime());
        userObject.setReservationId(userReservation.getReservationId());
        userObject.setStatus(userReservation.getStatus());
        userObject.setStoreId(userReservation.getStoreId());
        userObject.setUserEmail(userReservation.getUserEmail());
    }

    /**
     * @param storeId - Store Id of Store
     * @return - It returns all the reservations processed by the store
     */

    public ResponseEntity<String> findStoreReservations(Long storeId) {

        log.info("-----find Store Reservation called-----");

        Store store = storeDao.findByStoreId(storeId);
        String storeName = store.getStore_name();
        List<StoreReservationDetails> storeReservationsDetails = new ArrayList<StoreReservationDetails>();
        List<Reservation> storeReservations = new ArrayList<Reservation>();
        storeReservations = reservationRepository.findByStoreId(storeId);
        if (!storeReservations.isEmpty()) {
            for (Reservation storeReservation : storeReservations) {
                StoreReservationDetails storeObject = new StoreReservationDetails();
                setStoreReservation(storeObject, storeReservation);
                storeReservationsDetails.add(storeObject);

            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            String storeReservationsJson;
            try {
                storeReservationsJson = objectMapper.writeValueAsString(storeReservationsDetails);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<String>("{\"message\":\"" + "JSON Parsing Error" + "\"}", HttpStatus.OK);
            }

            String storeInfo = "storeReservationDetails";
            String response = "[{\"" + storeInfo + "\":" + storeReservationsJson
                    + ",\"storeName\":" + "\"" + storeName + "\"}" + "]";

            return new ResponseEntity<String>(response, HttpStatus.OK);

        } else {
            return new ResponseEntity<String>(
                    "{\"message\":\"" + "No reservations found."
                            + "\"}",
                    HttpStatus.OK);
        }
    }

    private void setStoreReservation(StoreReservationDetails storeObject, Reservation storeReservation) {

        Drugs drugDetails = drugsDao.findByDrugId(storeReservation.getDrugId());
        String userName = null;
        Optional<User> userDetails = userRepository.findByEmail(storeReservation.getUserEmail());
        if (userDetails.isPresent()) {
            userName = userDetails.get().getName();
        }
        String drugName = drugDetails.getDrug_name();
        Float unitPrice = storeReservation.getUnitPrice();
        storeObject.setUnitPrice(unitPrice);
        storeObject.setDrugName(drugName);
        storeObject.setUserName(userName);
        storeObject.setDrugId(storeReservation.getDrugId());
        storeObject.setLockAcquiredTime(storeReservation.getLockAcquiredTime());
        storeObject.setQuantityNeeded(storeReservation.getQuantityNeeded());
        storeObject.setReleaseTime(storeReservation.getReleaseTime());
        storeObject.setReservationId(storeReservation.getReservationId());
        storeObject.setStatus(storeReservation.getStatus());
        storeObject.setStoreId(storeReservation.getStoreId());
        storeObject.setUserEmail(storeReservation.getUserEmail());

    }

}
