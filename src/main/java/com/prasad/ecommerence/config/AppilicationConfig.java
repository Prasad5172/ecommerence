package com.prasad.ecommerence.config;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prasad.ecommerence.service.CustomUserServiceImplementation;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppilicationConfig {

    private final CustomUserServiceImplementation customUserServiceImplementation;

    @Autowired
    @Qualifier("jwtAuthenticationProvider")
    JwtAuthenticationProvider jwtAuthenticationProvider;
    


    

    @Bean
    public UserDetailsService userDetailsService() {
        // System.out.println("UserDetailsService");
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                UserDetails user = customUserServiceImplementation.loadUserByUsername(email);
                if (user == null) {
                    throw new UsernameNotFoundException(email + "does exists");
                }
                return user;
            }
        };
    }

    // Default Authentication Provider 
    @Bean
    @Qualifier("authenticationProvider")
    public AuthenticationProvider authenticationProvider() {
        // System.out.println("AuthenticationProvider");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // System.out.println("passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    // Default AuthenticationManager 
    @Bean
    @Qualifier("authenticationManager")
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        // authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        // you need to change the area where you are fetching user from userRepositry when you added this below provider for instace i added only jwtProvider
        // authenticationManagerBuilder.authenticationProvider(cloudAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

}



@Service
@Qualifier("jwtAuthenticationProvider")
class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserServiceImplementation customUserServiceImplementation;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // System.out.println("JwtAuthenticateProvider");
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        UserDetails user = customUserServiceImplementation.loadUserByUsername(username);
        System.out.println(encoder.matches(password, user.getPassword()));
        if (user != null) {
            if (encoder.matches(password, user.getPassword())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password,
                        new ArrayList<>());
                return token;
            }
        }
        throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}


