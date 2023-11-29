package com.prasad.ecommerence.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.config.JwtUtils;
import com.prasad.ecommerence.exception.CustomException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.UserRepository;
import com.prasad.ecommerence.request.LoginRequest;
import com.prasad.ecommerence.responce.JwtResponce;
import com.prasad.ecommerence.service.CartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CartService cartService;

    @PostMapping("signup")
    public ResponseEntity<JwtResponce> saveUser(@RequestBody User user) throws UserException {
        // System.out.println("register route");
        var userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb != null) {
            throw new CustomException("Email is already registred");
        }

        var saveUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .localDateTime(LocalDateTime.now())
                .build();
        saveUser = userRepository.save(saveUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(),
                saveUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Cart cart =cartService.createCart(saveUser);
        String token = jwtUtils.generateToken(saveUser);
        return ResponseEntity.ok(JwtResponce.builder().accesstoken(token).message("saved user").build());
    }


    @PostMapping("signin")
    public ResponseEntity<JwtResponce> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,
                password);
        Authentication authentication = authenticationManager
                .authenticate(authToken);
        if (authentication.isAuthenticated()) {
            var user = userRepository.findByEmail(email);
            var token = jwtUtils.generateToken(user);
            return ResponseEntity.ok(JwtResponce.builder().accesstoken(token).message("singin succesful").build());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

    }

}
