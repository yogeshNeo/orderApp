package com.orderApp.controller;

import com.orderApp.model.Order;
import com.orderApp.model.OrderEvent;
import com.orderApp.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/orders")
    public void createOrder(@RequestBody Order order) {
        try {
            order.setStatus("CREATED");
            Order savedOrder = orderRepo.save(order);
            OrderEvent event = new OrderEvent();
            event.setOrder(savedOrder);
            event.setType("ORDER_CREATED");
            this.kafkaTemplate.send("new-orders", event);
            //   return savedOrder;
        } catch (Exception e) {
            log.info("Error {}", e.getMessage());
            order.setStatus("FAILED");
            orderRepo.save(order);
        }
    }

    @GetMapping("/orders")
    public List<Order> getOrder() {
        return orderRepo.findAll();
    }


}
