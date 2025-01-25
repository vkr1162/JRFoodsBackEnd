package com.jrfoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.Product;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.ProductException;
import com.jrfoods.model.AddProductBinding;
import com.jrfoods.model.CartBinding;
import com.jrfoods.repository.CartItemRepository;
import com.jrfoods.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;

	@Override
	public Cart createCart(UserDtls userDtls) {
		
		Cart cart = new Cart();
		
		cart.setUserDtls(userDtls);
		
		Cart cartSaved = cartRepo.save(cart);
		return cartSaved;
	}

	@Override
	public String addCartItem(Integer userId, CartBinding addProduct) throws ProductException {
		
		Cart cart = cartRepo.findByUserId(userId);
		
		Product product = productService.findProductById(addProduct.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExists(cart, product, addProduct.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(addProduct.getQuantity());
			cartItem.setUserId(userId);
			
			Integer price = addProduct.getQuantity()*product.getDiscPercentage();
			cartItem.setPrice(price);
			cartItem.setSize(addProduct.getSize());
			
			CartItem cartItemCreated = cartItemService.createCartItem(cartItem);
			
			cart.getCartItems().add(cartItemCreated);
		}
		return "Item Added to Cart";
	}

	@Override
	public Cart findUserCart(Integer userId) {
		
		Cart cart = cartRepo.findByUserId(userId);
		
		Integer totalPrice = 0;
		
		Integer totalDiscountedPrice = 0;
		
		Integer totalItem = 0;
		
		for(CartItem cartItem: cart.getCartItems()) {
			totalPrice+=cartItem.getPrice();
			totalDiscountedPrice+=cartItem.getDiscountedPrice();
			totalItem+=cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);	
		cart.setTotalItem(totalItem);
		
		return cartRepo.save(cart);
	}

}
