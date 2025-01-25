package com.jrfoods.service;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.Product;
import com.jrfoods.exception.CartItemException;
import com.jrfoods.exception.UserException;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Integer userId, Integer id, CartItem CartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExists(Cart cart, Product product, String size, Integer userId);
	
	public String removeCartItem(Integer userId, Integer cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Integer cartItemId) throws CartItemException;
	
	

}
