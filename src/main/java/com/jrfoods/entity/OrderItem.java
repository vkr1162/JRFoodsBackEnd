package com.jrfoods.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	private Order order;

	@ManyToOne
	private Product product;
	
	private String size;
	
	private Integer quantity;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Integer userId;
	
	private LocalDateTime deliveryDate;
}
