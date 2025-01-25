package com.jrfoods.service;

import java.util.List;

import com.jrfoods.entity.Address;
import com.jrfoods.entity.Order;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.OrderException;
import com.jrfoods.model.AddressBinding;

public interface OrderService {
 
	public Order createOrder(UserDtls userDtls, Address shippingAddress);
	
	public List<Order> usersOrderHistory(Integer userId);
	
	public Order findOrderById(Integer orderId) throws OrderException;
	
	public Order placedOrder(Integer orderId) throws OrderException;
	
	public Order confirmedOrder(Integer orderId) throws OrderException;
	
	public Order shippedOrder(Integer orderId) throws OrderException;
	
	public Order deliveredOrder(Integer orderId) throws OrderException;
	
	public Order cancelledOrder(Integer orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public String deleteOrder(Integer orderId) throws OrderException;
	 
	
}
