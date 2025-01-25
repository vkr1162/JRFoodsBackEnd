package com.jrfoods.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jrfoods.entity.Product;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.CreateProductBinding;

public interface ProductService {
	
	public Product createProduct(CreateProductBinding req);
	
	public String deleteProduct(Integer productId) throws ProductException;
	
	public Product updateProduct(Integer productId, Product productToUpdate) throws ProductException;
	
	public Product findProductById(Integer productId) throws ProductException;
	
	public Page<Product> getAllProducts(String Category, List<String> sizes, Integer minPrice, Integer maxPrice,
										Integer minDiscount, String sort, String stock, Integer pageNo, Integer pageSize);
	
	public List<Product> getProductByCategory(String Category);

	public List<Product> getAllProducts();

}
