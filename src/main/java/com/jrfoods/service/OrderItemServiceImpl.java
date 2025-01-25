package com.jrfoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.OrderItem;
import com.jrfoods.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService{

	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepo.save(orderItem);
	}
	

}
