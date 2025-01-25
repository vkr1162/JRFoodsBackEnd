package com.jrfoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrfoods.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
