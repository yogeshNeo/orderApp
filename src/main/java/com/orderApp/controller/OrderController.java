package com.orderApp.controller;

import com.orderApp.model.CustomerOrder;
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
    public void createOrder(@RequestBody CustomerOrder customerOrder) {

        Order order = new Order();
        try {
            // save order in database
            order.setAmount(customerOrder.getAmount());
            order.setItem(customerOrder.getItem());
            order.setQuantity(customerOrder.getQuantity());
            order.setStatus("CREATED");
            order = orderRepo.save(order);

            customerOrder.setOrderId(order.getId());
            OrderEvent event = new OrderEvent();
            event.setOrder(customerOrder);
            event.setType("ORDER_CREATED");
            log.info("order saved {} and kafka new-orders topic publish :::::", order.getId());
            this.kafkaTemplate.send("new-orders", event);
        } catch (Exception e) {
            log.info("order saved failed :: ");
            order.setStatus("FAILED");
            orderRepo.save(order);
        }
    }

    @GetMapping("/orders")
    public List<Order> getOrder() {
        return orderRepo.findAll();
    }

}
