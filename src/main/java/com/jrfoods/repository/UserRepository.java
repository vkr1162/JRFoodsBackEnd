package com.jrfoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrfoods.entity.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
	
	public UserDtls findByEmail(String email);

}
