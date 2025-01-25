package com.jrfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.LoginRequest;
import com.jrfoods.response.AuthResponse;
import com.jrfoods.service.CartService;
import com.jrfoods.service.JwtProvider;
import com.jrfoods.service.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDtls userDtls) throws UserException{
		
		
		UserDtls userSaved = userService.createUser(userDtls);
		Cart cart = cartService.createCart(userSaved);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), userSaved.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(token, "User Successfully Signed Up");
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException{
		
		AuthResponse authResponse  = userService.loginUser(loginRequest);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

}
