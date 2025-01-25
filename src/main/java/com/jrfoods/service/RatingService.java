package com.jrfoods.service;

import java.util.List;

import com.jrfoods.entity.Rating;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.RatingBinding;

public interface RatingService {

	public Rating createRating(RatingBinding rate, UserDtls userDtls) throws ProductException;
	
	public List<Rating> getProductsRating(Integer productId);
	
	
}
