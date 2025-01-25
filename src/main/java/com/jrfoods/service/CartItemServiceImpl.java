package com.jrfoods.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.Product;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.CartItemException;
import com.jrfoods.exception.UserException;
import com.jrfoods.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private UserService userService;

	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscPercentage()*cartItem.getQuantity());
		
		CartItem cartItemSaved = cartItemRepo.save(cartItem);
		return cartItemSaved;
		
	}

	@Override
	public CartItem updateCartItem(Integer userId, Integer id, CartItem cartItem)
			throws CartItemException, UserException {
		
		CartItem item = findCartItemById(id);
		
		UserDtls userDtls = userService.findUserById(item.getUserId());
		
		if(userDtls.getUserId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscPercentage()*item.getQuantity());
			
		}
		
		return cartItemRepo.save(item); 
	}

	@Override
	public CartItem isCartItemExists(Cart cart, Product product, String size, Integer userId) {
		
		CartItem cartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public String removeCartItem(Integer userId, Integer cartItemId) throws CartItemException, UserException {
		
		CartItem cartItem = findCartItemById(cartItemId);
		
		UserDtls userDtls = userService.findUserById(cartItem.getUserId());
		
		UserDtls reqUser = userService.findUserById(userId);
		
		if(userDtls.getUserId().equals(reqUser.getUserId())) {
			cartItemRepo.deleteById(cartItemId);
			return "Item deleted with Id" + cartItemId;
		}
		else {
			throw new UserException("You can't remove another user Cart Item");
		}
		
	}

	@Override
	public CartItem findCartItemById(Integer cartItemId) throws CartItemException {
		
		Optional<CartItem> opt = cartItemRepo.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new CartItemException("Cart Item not Found with id: " + cartItemId);
	}

}
