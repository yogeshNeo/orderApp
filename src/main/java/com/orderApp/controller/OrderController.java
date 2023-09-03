package com.orderApp.controller;

import com.orderApp.model.Order;
import com.orderApp.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

   /* @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;*/

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        try {
            order.setStatus("CREATED");
            return orderRepo.save(order);
/*            customerOrder.setOrderId(order.getId());
            Order event = new Order();
            event.setOrder(customerOrder);
            event.setType("ORDER_CREATED");*/

            //  this.kafkaTemplate.send("new-orders", event);
        } catch (Exception e) {
            log.info("ErrorSS {}", e.getMessage());
            e.printStackTrace();
            order.setStatus("FAILEDSS");
            return orderRepo.save(order);
        }
    }

    @GetMapping("/orders")
    public List<Order> getOrder() {
        return orderRepo.findAll();
    }


}
