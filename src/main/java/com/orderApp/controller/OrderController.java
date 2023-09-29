package com.orderApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderApp.model.CustomerOrder;
import com.orderApp.model.Order;
import com.orderApp.model.OrderEvent;
import com.orderApp.repository.OrderRepository;
import com.orderApp.utility.Common;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;

    private final Common common;
    private final ObjectMapper objectMapper;
    private final JedisCluster redisClient;
    String ORDER_CACHE_KEY = "orderCacheKey";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    @Value("${redis.orderCacheExpireIn}")
    private int orderCacheExpireIn;

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
        log.info("Inside getOrder ::");
        long count = orderRepo.count();
        String data = redisClient.get(ORDER_CACHE_KEY);

        try {
            List<Order> stringToListObject;
            if (!StringUtils.isBlank(data)) {
                stringToListObject = common.jsonStringToListObject(data, Order.class);
                if (stringToListObject.size() == count) {
                    log.info("fetch from redis :: ");
                    return stringToListObject;
                } else {
                    log.info("redis-db count unmatched !! ");
                    return fetchOrderFromDB();
                }
            } else {
                log.info("redis blank data !! ");
                return fetchOrderFromDB();
            }
        } catch (Exception e) {
            log.info("Error while fetching orders and call db {}", e.toString());
            return orderRepo.findAll();
        }
    }

    private List<Order> fetchOrderFromDB() {
        log.info("call for db :: ");
        List<Order> orderList = orderRepo.findAll();
        final String cacheObjectToJsonString = common.objectToJsonString(orderList);
        redisClient.set(ORDER_CACHE_KEY, cacheObjectToJsonString);
        redisClient.expire(ORDER_CACHE_KEY, orderCacheExpireIn);
        return orderList;
    }

}
