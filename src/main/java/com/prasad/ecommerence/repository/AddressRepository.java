package com.prasad.ecommerence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasad.ecommerence.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    
}
