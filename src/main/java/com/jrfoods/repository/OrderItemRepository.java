package com.jrfoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrfoods.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
