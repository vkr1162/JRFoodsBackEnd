package com.jrfoods.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Product;
import com.jrfoods.entity.Review;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.ReviewBinding;
import com.jrfoods.repository.ProductRepository;
import com.jrfoods.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private ProductService productService;
	
	
	
	@Override
	public Review createReview(ReviewBinding rev, UserDtls userDtls) throws ProductException {
		
		Product product = productService.findProductById(rev.getProductId());
		
		Review review = new Review();
		
		review.setUserDtls(userDtls);
		review.setProduct(product);
		review.setReview(rev.getReviewMessage());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReviews(Integer productId) {
		
		return reviewRepo.getAllProductsReviews(productId);
	}

	
	
}
