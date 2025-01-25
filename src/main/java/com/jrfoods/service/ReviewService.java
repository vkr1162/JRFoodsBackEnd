package com.jrfoods.service;

import java.util.List;

import com.jrfoods.entity.Review;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.ReviewBinding;

public interface ReviewService {
	
	public Review createReview(ReviewBinding rev, UserDtls userDtls) throws ProductException;
	
	public List<Review> getAllReviews(Integer productId);

}
