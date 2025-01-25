package com.jrfoods.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Product;
import com.jrfoods.exception.ProductException;
import com.jrfoods.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<Product>> findProductByPage(
			@RequestParam String category,
			@RequestParam List<String>size,
			@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,
			@RequestParam Integer minDiscount,
			@RequestParam String sort,
			@RequestParam String stock,
			@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize
			){
	
		Page<Product> result = productService.getAllProducts(category, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
		return new ResponseEntity<Page<Product>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/id/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Integer productId) throws ProductException{
		
		Product product = productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	
}
