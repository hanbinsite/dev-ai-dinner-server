package com.example.dining.service;

import com.example.dining.entity.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    /**
     * 创建订单
     */
    Mono<Order> createOrder(Order order);

    /**
     * 更新订单状态
     */
    Mono<Void> updateOrderStatus(Long orderId, Integer status);

    /**
     * 根据ID查询订单
     */
    Mono<Order> findById(Long id);

    /**
     * 根据订单号查询订单
     */
    Mono<Order> findByOrderNo(String orderNo);

    /**
     * 查询用户的订单列表
     */
    Flux<Order> findByUserId(String userId);

    /**
     * 查询桌号的订单列表
     */
    Flux<Order> findByTableNo(String tableNo);
} 