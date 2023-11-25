package com.prasad.ecommerence.service;

import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.User;

public interface UserService {
    public User findUserById(Long id) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
}
