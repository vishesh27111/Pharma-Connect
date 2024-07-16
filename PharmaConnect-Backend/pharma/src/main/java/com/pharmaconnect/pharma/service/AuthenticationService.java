package com.pharmaconnect.pharma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.dao.UserDao;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.entity.User;
import com.pharmaconnect.pharma.exceptions.UserRegistrationException;
import com.pharmaconnect.pharma.model.JwtAuthenticationResponseStore;
import com.pharmaconnect.pharma.model.JwtAuthenticationResponseUser;
import com.pharmaconnect.pharma.model.SignInStoreRequest;
import com.pharmaconnect.pharma.model.SignInUserRequest;
import com.pharmaconnect.pharma.model.SignUpStoreRequest;
import com.pharmaconnect.pharma.model.SignUpUserRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreDao storeRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    /**
     * @param request - It contains the details given by the customer
     * @return - It returns the Registration Status of the Customer
     */

    public ResponseEntity<JwtAuthenticationResponseUser> signUpUser(SignUpUserRequest request) {
        authenticationProvider.setUserDetailsService(userService);
        if (containsNullValues(request)) {
            return createBadRequestResponse("Null values found.");
        }

        if (userExists(request.getEmail())) {
            return createExpectationFailedResponse("User already exists.");
        }

        User user = saveUser(request);
        String jwt = jwtService.generateToken(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildJwtAuthenticationResponseUser(jwt, user.getEmail()));
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User saveUser(SignUpUserRequest request) {
        User user = buildUserFromRequest(request);
        try {
            return userService.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserRegistrationException("Failed to register the user.");
        }
    }

    private JwtAuthenticationResponseUser buildJwtAuthenticationResponseUser(String jwt, String email) {
        return JwtAuthenticationResponseUser.builder()
                .token(jwt)
                .errorMessage(null)
                .emailId(email)
                .build();
    }

    private boolean containsNullValues(SignUpUserRequest request) {
        return isNullOrEmpty(request.getAddress())
                || isNullOrEmpty(request.getEmail())
                || isNullOrEmpty(request.getName())
                || isNullOrEmpty(request.getPassword())
                || isNullOrEmpty(request.getPhone())
                || isNullOrEmpty(request.getZipcode())
                || request.getCreatedAt() == null
                || request.getUpdatedAt() == null
                || request.getDate_Of_Birth() == null;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    protected ResponseEntity<JwtAuthenticationResponseUser> createBadRequestResponse(String errorMessage) {
        JwtAuthenticationResponseUser response = new JwtAuthenticationResponseUser();
        response.setToken(null);
        response.setErrorMessage(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    protected ResponseEntity<JwtAuthenticationResponseUser> createExpectationFailedResponse(String errorMessage) {
        JwtAuthenticationResponseUser response = new JwtAuthenticationResponseUser();
        response.setToken(null);
        response.setErrorMessage(errorMessage);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
    }

    private User buildUserFromRequest(SignUpUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setZipcode(request.getZipcode());
        user.setDate_Of_Birth(request.getDate_Of_Birth());
        user.setCreatedAt(request.getCreatedAt());
        user.setUpdatedAt(request.getUpdatedAt());
        return user;
    }

    /**
     * @param request - It contains the details given by the customer
     * @return - It returns the Sign In status of the customer
     */
    public JwtAuthenticationResponseUser signInUser(SignInUserRequest request) {
        authenticationProvider.setUserDetailsService(userService);
        authenticateUser(request.getEmail(), request.getPassword());

        User user = findUserByEmail(request.getEmail());

        JwtAuthenticationResponseUser responseBuilder = buildJwtAuthenticationResponse(user);

        return responseBuilder;
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    private JwtAuthenticationResponseUser buildJwtAuthenticationResponse(User user) {
        String jwt = jwtService.generateToken(user);

        JwtAuthenticationResponseUser responseBuilder = new JwtAuthenticationResponseUser();
        responseBuilder.setToken(jwt);
        responseBuilder.setEmailId(user.getEmail());
        responseBuilder.setUserId(user.getUserId());
        return responseBuilder;
    }

    /**
     * @param request - It contains the details given by the Store
     * @return - It returns the Registration Status of the Store
     */
    public JwtAuthenticationResponseStore signUpStore(SignUpStoreRequest request) {
        authenticationProvider.setUserDetailsService(storeService);
        Store store = buildStoreFromRequest(request);
        store = saveStore(store);
        String jwt = jwtService.generateToken(store);
        return buildJwtAuthenticationResponseStore(jwt, store.getStoreId());
    }

    private Store buildStoreFromRequest(SignUpStoreRequest request) {
        Store store = new Store();
        store.setStore_name(request.getStore_name());
        store.setManager_name(request.getManager_name());
        store.setAddress(request.getAddress());
        store.setEmail(request.getEmail());
        store.setPassword(passwordEncoder.encode(request.getPassword()));
        store.setPhone(request.getPhone());
        store.setZipcode(request.getZipcode());
        store.setRegistration_number(request.getRegistration_number());
        store.setCreatedAt(request.getCreatedAt());
        store.setUpdatedAt(request.getUpdatedAt());
        return store;
    }

    private Store saveStore(Store store) {
        return storeService.save(store);
    }

    private JwtAuthenticationResponseStore buildJwtAuthenticationResponseStore(String jwt, Long storeId) {
        return JwtAuthenticationResponseStore.builder().token(jwt).storeId(storeId).build();
    }

    /**
     * @param request - It contains the details given by the customer
     * @return - It returns the Sign In status of the store
     */
    public JwtAuthenticationResponseStore signInStore(SignInStoreRequest request) {
        authenticationProvider.setUserDetailsService(storeService);
        authenticateStore(request.getEmail(), request.getPassword());
        Store store = findStoreByEmail(request.getEmail());
        String jwt = jwtService.generateToken(store);
        return buildJwtAuthenticationResponseStore(jwt, store.getStoreId());
    }

    private void authenticateStore(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private Store findStoreByEmail(String email) {
        return storeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

}