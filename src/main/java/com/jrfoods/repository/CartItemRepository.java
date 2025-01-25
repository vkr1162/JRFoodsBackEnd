package com.jrfoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrfoods.entity.Cart;
import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product, @Param("size")String size, @Param("userId")Integer userId);
}
