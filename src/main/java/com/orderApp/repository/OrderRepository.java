package com.orderApp.repository;

import com.orderApp.model.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    List<Order> findAll();
}
