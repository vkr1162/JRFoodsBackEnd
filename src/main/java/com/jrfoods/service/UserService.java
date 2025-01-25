package com.jrfoods.service;

import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.LoginRequest;
import com.jrfoods.response.AuthResponse;

public interface UserService {

	public UserDtls findUserById(Integer userId) throws UserException;
	
	public UserDtls findUserByJwt(String jwt) throws UserException;
	
	public UserDtls createUser(UserDtls userDtls) throws UserException;
	
	public AuthResponse loginUser(LoginRequest loginRequest) throws UserException;
}
