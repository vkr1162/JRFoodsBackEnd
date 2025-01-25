package com.jrfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Order;
import com.jrfoods.exception.OrderException;
import com.jrfoods.response.ApiResponse;
import com.jrfoods.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrders(){
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrder(@PathVariable Integer orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/shipping")
	public ResponseEntity<Order> shippedOrder(@PathVariable Integer orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
		Order order = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/deliver")
	public ResponseEntity<Order> deliverOrder(@PathVariable Integer orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/cancel")
	public ResponseEntity<Order> cancelOrder(@PathVariable Integer orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
		Order order = orderService.cancelledOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrder(@PathVariable Integer orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiResponse response = new ApiResponse();
		response.setMessage("Order Deleted Successfully");
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
} 
