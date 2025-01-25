package com.jrfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.AddProductBinding;
import com.jrfoods.model.CartBinding;
import com.jrfoods.response.ApiResponse;
import com.jrfoods.service.CartService;
import com.jrfoods.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart Management", description="Find User Cart, add item to cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	@Operation(description = "find cart by user id")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException{
		UserDtls user = userService.findUserByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getUserId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	@Operation(description = "Add Item to Cart")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartBinding addItem, @RequestHeader("Authorization")String jwt) throws UserException,ProductException{
		UserDtls userDtls = userService.findUserByJwt(jwt);
		cartService.addCartItem(userDtls.getUserId(), addItem);
		ApiResponse res = new ApiResponse();
		res.setMessage("Item added to Cart");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
}
