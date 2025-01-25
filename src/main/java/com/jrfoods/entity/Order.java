package com.jrfoods.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="orders")
public class Order {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Integer id;
	
	private String orderId;
	
	@ManyToOne
	private UserDtls userDtls;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private LocalDateTime OrderDate;
	
	private LocalDateTime deliveryDate;
	
	@OneToOne
	private Address shippingAddress;
	
	@Embedded
	private PaymentDetails paymentDetials = new PaymentDetails();
	
	private double totalPrice;
	
	private Integer totalDiscountedPrice;
	
	private Integer discount;
	
	private String orderStatus;
	
	private int totalItems;
	
	private LocalDateTime createdAt;
}
