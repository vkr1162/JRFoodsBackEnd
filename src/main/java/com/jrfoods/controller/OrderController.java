package com.jrfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrfoods.entity.Address;
import com.jrfoods.entity.Order;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.OrderException;
import com.jrfoods.exception.UserException;
import com.jrfoods.model.AddressBinding;
import com.jrfoods.service.OrderService;
import com.jrfoods.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order> createdOrder(@RequestBody Address ShippingAddress, @RequestHeader("Authorization") String jwt) throws UserException{
		
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		Order order = orderService.createOrder(userDtls, ShippingAddress);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> getUsersOrderHistory(@RequestHeader("Authorization")String jwt) throws UserException{
		UserDtls userDtls = userService.findUserByJwt(jwt);
		
		List<Order> ordersList = orderService.usersOrderHistory(userDtls.getUserId());
		
		return new ResponseEntity<List<Order>>(ordersList,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("id")Integer orderId, @RequestHeader("Authorization")String jwt) throws UserException,OrderException{
		UserDtls userDtls = userService.findUserByJwt(jwt);
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	

}
