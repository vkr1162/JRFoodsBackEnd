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

import com.jrfoods.entity.Review;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.ReviewBinding;
import com.jrfoods.service.ReviewService;
import com.jrfoods.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewBinding reviewMessage, @RequestHeader("Authorization")String jwt) throws UserException, ProductException {
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		Review review = reviewService.createReview(reviewMessage, userDtls);
		
		return new ResponseEntity<Review>(review, HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getAllReviews(@PathVariable Integer productId) throws ProductException{
		
		List<Review> reviewList = reviewService.getAllReviews(productId);
		
		return new ResponseEntity<List<Review>>(reviewList,HttpStatus.OK);
	}
}
