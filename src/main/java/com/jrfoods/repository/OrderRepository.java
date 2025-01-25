package com.jrfoods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrfoods.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT o FROM Order o where o.userDtls.id=:userId AND (o.orderStatus='PLACED' OR o.orderStatus='CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus='DELIVERED')")
	public List<Order> getUserOrders(@Param("userId")Integer userId);
}
