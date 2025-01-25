package com.jrfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Product;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.CreateProductBinding;
import com.jrfoods.response.ApiResponse;
import com.jrfoods.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductBinding addProduct){
		
		Product product = productService.createProduct(addProduct);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) throws ProductException{
		productService.deleteProduct(productId);
		ApiResponse res = new ApiResponse();
		res.setMessage("Product Deleted Successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody Product productToUpdate) throws ProductException{
		Product product = productService.updateProduct(productId, productToUpdate);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Integer productId) throws ProductException{
		Product product = productService.findProductById(productId);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProducts(){
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@PostMapping("/createMultipleProducts")
	public ResponseEntity<ApiResponse> createMultipleProducts(@RequestBody CreateProductBinding[] addProducts){
		
		for(CreateProductBinding product:addProducts) {
			productService.createProduct(product);
		}
		ApiResponse res = new ApiResponse();
		res.setMessage("Products Created Successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.CREATED);
	}

}
