package com.jrfoods.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Product;
import com.jrfoods.entity.Rating;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.RatingBinding;
import com.jrfoods.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private ProductService productService;
	

	
	@Override
	public Rating createRating(RatingBinding rate, UserDtls userDtls) throws ProductException {
		
	Product product = productService.findProductById(rate.getProductId());
	
	Rating rating = new Rating();
	rating.setProduct(product);
	rating.setUserDtls(userDtls);
	rating.setRating(rate.getRating());
	rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Integer productId) {
		
		return ratingRepo.getAllProductsRating(productId);
 	}

}
