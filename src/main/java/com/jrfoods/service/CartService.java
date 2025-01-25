package com.jrfoods.service;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.AddProductBinding;
import com.jrfoods.model.CartBinding;


public interface CartService {

	public Cart createCart(UserDtls userDtls);
	
	public String addCartItem(Integer userId, CartBinding addProduct) throws ProductException;
	
	public Cart findUserCart(Integer userId);
	
	
}
