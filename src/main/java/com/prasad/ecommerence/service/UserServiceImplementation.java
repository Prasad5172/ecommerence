package com.prasad.ecommerence.service;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.config.JwtUtils;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        
        throw new UserException("user not found with id:");
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException{
        String email = jwtUtils.extractUsername(jwt.substring(7));
         User user = userRepository.findByEmail(email);
        if(user!= null){
            return user;
        }
        
        throw new UserException("user not found with id:");
    }

    
}
