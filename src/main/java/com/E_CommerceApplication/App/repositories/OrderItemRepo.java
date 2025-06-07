package com.E_CommerceApplication.App.repositories;

import com.E_CommerceApplication.App.models.Order;
import com.E_CommerceApplication.App.models.OrderItems;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItems, Long> {

    List<Order> saveAll(List<Order> orderItems);
}
