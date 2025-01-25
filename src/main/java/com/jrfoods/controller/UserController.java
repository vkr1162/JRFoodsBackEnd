package com.jrfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.UserException;
import com.jrfoods.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<UserDtls> getUserProfile(@RequestHeader("Authorization")String jwt) throws UserException{
		System.out.println(jwt);
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		return new ResponseEntity<UserDtls>(userDtls,HttpStatus.OK);
	}
}
