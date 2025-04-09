package com.example.dining.controller;

import com.example.dining.common.Result;
import com.example.dining.dto.OrderDTO;
import com.example.dining.entity.Order;
import com.example.dining.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Mono<Result<Order>> createOrder(@Validated @RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setTableNo(orderDTO.getTableNo());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setRemark(orderDTO.getRemark());
        
        return orderService.createOrder(order)
                .map(Result::success);
    }

    @PutMapping("/{id}/status")
    public Mono<Result<Void>> updateOrderStatus(@PathVariable Long id, @RequestParam Integer status) {
        return orderService.updateOrderStatus(id, status)
                .then(Mono.just(Result.success()));
    }

    @GetMapping("/{id}")
    public Mono<Result<Order>> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(Result::success);
    }

    @GetMapping("/orderNo/{orderNo}")
    public Mono<Result<Order>> getOrderByOrderNo(@PathVariable String orderNo) {
        return orderService.findByOrderNo(orderNo)
                .map(Result::success);
    }

    @GetMapping("/user/{userId}")
    public Flux<Result<Order>> getOrdersByUserId(@PathVariable String userId) {
        return orderService.findByUserId(userId)
                .map(Result::success);
    }

    @GetMapping("/table/{tableNo}")
    public Flux<Result<Order>> getOrdersByTableNo(@PathVariable String tableNo) {
        return orderService.findByTableNo(tableNo)
                .map(Result::success);
    }
} 