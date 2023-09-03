package com.orderApp.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderApp.model.Order;
import com.orderApp.model.OrderEvent;
import com.orderApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReverseOrder {

    @Autowired
    private OrderRepository repository;

    @KafkaListener(topics = "reversed-orders", groupId = "orders-group")
    public void reverseOrder(String event) {
        try {
            OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);
            Optional<Order> order = this.repository.findById(orderEvent.getOrder().getId());

            order.ifPresent(o -> {
                o.setStatus("FAILED");
                this.repository.save(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}