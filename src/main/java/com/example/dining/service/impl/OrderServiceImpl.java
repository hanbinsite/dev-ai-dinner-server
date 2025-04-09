package com.example.dining.service.impl;

import com.example.dining.common.OrderStatus;
import com.example.dining.common.util.OrderNoGenerator;
import com.example.dining.entity.Order;
import com.example.dining.entity.OrderItem;
import com.example.dining.mapper.OrderItemMapper;
import com.example.dining.mapper.OrderMapper;
import com.example.dining.service.OrderService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Mono<Order> createOrder(Order order) {
        return Mono.fromRunnable(() -> {
            // 生成订单号
            String orderNo = OrderNoGenerator.generateOrderNo();
            order.setOrderNo(orderNo);
            order.setStatus(OrderStatus.ORDERED.getCode()); // 设置初始状态为已下单
            
            // 保存订单
            orderMapper.insert(order);
            
            // 保存订单项
            if (order.getOrderItems() != null) {
                for (OrderItem item : order.getOrderItems()) {
                    item.setOrderId(order.getId());
                    orderItemMapper.insert(item);
                }
            }
        }).thenReturn(order);
    }

    @Override
    public Mono<Void> updateOrderStatus(Long orderId, Integer status) {
        return Mono.fromRunnable(() -> {
            // 验证状态是否有效
            OrderStatus.fromCode(status);
            orderMapper.updateStatus(orderId, status);
        });
    }

    @Override
    public Mono<Order> findById(Long id) {
        return Mono.fromCallable(() -> {
            Order order = orderMapper.findById(id);
            if (order != null) {
                order.setOrderItems(orderItemMapper.findByOrderId(id));
            }
            return order;
        });
    }

    @Override
    public Mono<Order> findByOrderNo(String orderNo) {
        return Mono.fromCallable(() -> {
            Order order = orderMapper.findByOrderNo(orderNo);
            if (order != null) {
                order.setOrderItems(orderItemMapper.findByOrderId(order.getId()));
            }
            return order;
        });
    }

    @Override
    public Flux<Order> findByUserId(String userId) {
        return Flux.fromIterable(orderMapper.findByUserId(userId))
                .flatMap(order -> {
                    order.setOrderItems(orderItemMapper.findByOrderId(order.getId()));
                    return Mono.just(order);
                });
    }

    @Override
    public Flux<Order> findByTableNo(String tableNo) {
        return Flux.fromIterable(orderMapper.findByTableNo(tableNo))
                .flatMap(order -> {
                    order.setOrderItems(orderItemMapper.findByOrderId(order.getId()));
                    return Mono.just(order);
                });
    }
} 