package com.prasad.ecommerence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasad.ecommerence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    public User findByEmail(String email);
}
