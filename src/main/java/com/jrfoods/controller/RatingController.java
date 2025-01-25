package com.jrfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Rating;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.RatingBinding;
import com.jrfoods.service.RatingService;
import com.jrfoods.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingBinding ratingProvided, @RequestHeader("Authorization")String jwt) throws UserException,ProductException{
		
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		Rating rating = ratingService.createRating(ratingProvided, userDtls);
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Integer productId, @RequestHeader("Authorization")String jwt) throws UserException, ProductException{
		
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		List<Rating> ratings = ratingService.getProductsRating(productId);
		
		return new ResponseEntity<List<Rating>>(ratings,HttpStatus.OK);
	}
	
}
