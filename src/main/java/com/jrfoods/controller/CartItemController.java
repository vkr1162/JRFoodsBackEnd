package com.jrfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.CartItemException;
import com.jrfoods.exception.UserException;
import com.jrfoods.response.ApiResponse;
import com.jrfoods.service.CartItemService;
import com.jrfoods.service.UserService;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;

	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@RequestHeader("Authorization")String jwt, @PathVariable Integer cartItemId, @RequestBody CartItem cartItem) throws UserException, CartItemException{
		
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		CartItem updatedCartItem = cartItemService.updateCartItem(userDtls.getUserId(), cartItemId, cartItem);
		
		return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@RequestHeader("Authorization")String jwt, @PathVariable Integer cartItemId)  throws CartItemException,UserException{
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		String msg = cartItemService.removeCartItem(userDtls.getUserId(), cartItemId);
		ApiResponse res = new ApiResponse();
		res.setMessage(msg);
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
}
