package com.pharmaconnect.pharma.filter;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pharmaconnect.pharma.service.JwtService;
import com.pharmaconnect.pharma.service.StoreService;
import com.pharmaconnect.pharma.service.UserService;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserService userService;
    private final StoreService storeService;

    // Constructor

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            String jwt = authHeader.substring(BEARER_PREFIX.length());
            log.debug("JWT - {}", jwt);

            String userEmail = jwtService.extractUserName(jwt);

            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("URL - {}", request.getContextPath());

                UserDetails userDetails;
                if (request.getServletPath().startsWith("/user")) {
                    userDetails = userService.loadUserByUsername(userEmail);
                } else {
                    userDetails = storeService.loadUserByUsername(userEmail);
                }

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.debug("User - {}", userDetails);
                    setAuthenticationContext(request, userDetails);
                }
            }
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Exception during filter chain processing", e);
            throw e;
        }
    }

    private void setAuthenticationContext(HttpServletRequest request, UserDetails userDetails) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }

}