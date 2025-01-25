package com.jrfoods.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrfoods.entity.Address;
import com.jrfoods.entity.Cart;
import com.jrfoods.entity.CartItem;
import com.jrfoods.entity.Order;
import com.jrfoods.entity.OrderItem;
import com.jrfoods.entity.UserDtls;
import com.jrfoods.exception.OrderException;
import com.jrfoods.model.AddressBinding;
import com.jrfoods.repository.AddressRepository;
import com.jrfoods.repository.CartRepository;
import com.jrfoods.repository.OrderItemRepository;
import com.jrfoods.repository.OrderRepository;
import com.jrfoods.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

	
	
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public Order createOrder(UserDtls userDtls, Address shippingAddress) {
		
		shippingAddress.setUserDtls(userDtls);
		Address address = addressRepo.save(shippingAddress);
		userDtls.getUserAddress().add(address);
		userRepo.save(userDtls);
		
		Cart cart = cartService.findUserCart(userDtls.getUserId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepo.save(orderItem);
			orderItems.add(createdOrderItem);
		}
		Order createdOrder = new Order();
		createdOrder.setUserDtls(userDtls);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItems(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetials().setPaymentStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order orderSaved = orderRepo.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(orderSaved);
			orderItemRepo.save(item);
		}
		return orderSaved;
	}

	@Override
	public List<Order> usersOrderHistory(Integer userId) {
		List<Order> orders = orderRepo.getUserOrders(userId);
		return orders;
	}

	@Override
	public Order findOrderById(Integer orderId) throws OrderException {
		Optional<Order> opt = orderRepo.findById(orderId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("Order not Exist with Id " + orderId);
	}

	@Override
	public Order placedOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetials().setPaymentStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepo.save(order);
	}

	@Override
	public Order cancelledOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepo.findAll();
	}

	@Override
	public String deleteOrder(Integer orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepo.deleteById(orderId);
		return "Order deleted with Id " + orderId;
	}

	
}
