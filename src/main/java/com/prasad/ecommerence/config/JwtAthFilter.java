package com.prasad.ecommerence.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.prasad.ecommerence.service.CustomUserServiceImplementation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class JwtAthFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    private  CustomUserServiceImplementation customUserServiceImplementation;

    @Autowired
    private JwtUtils jwtUtils;
   
    public JwtAthFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("doFilterInternal");
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        final String jwtToken;
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                System.out.println("authheader is null");
                filterChain.doFilter(request, response);
                return;
            }
            jwtToken = authHeader.substring(7);
            userEmail = jwtUtils.extractUsername(jwtToken);
            System.out.println(userEmail);

            if (userEmail != null) {
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    // System.out.println("securityContext is null in dofilter method");
                    UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userEmail);
                    // var userTokenFromDB = tokenRepository.findByToken(jwtToken);
                    // System.out.println(userTokenFromDB);
                    // if (userTokenFromDB.isEmpty()) {
                    //     throw new TokenException(jwtToken, "token is not valid");
                    // }
                    // Boolean isTokenValid = userTokenFromDB
                    //         .map(t -> !t.getExpired() && !t.getRevoked()).orElse(false);
                    // Boolean isTokenValid = true;
                    // if (!isTokenValid) {
                    //     throw new ExpiredJwtException(null, null, "token is expired");
                    // }
                    if (jwtUtils.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, userDetails.getAuthorities());
                        authToken.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
            // System.out.println("endOfFilterChain");
        } catch (Exception ex) {
            System.out.println("exception");
            SecurityContextHolder.clearContext();
            System.out.println(ex);
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

}
