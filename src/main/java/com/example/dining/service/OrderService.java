package com.example.dining.service;

import com.example.dining.entity.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    /**
     * 创建订单
     * @param order 订单信息
     * @return 创建的订单
     */
    Mono<Order> createOrder(Order order);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 状态
     * @return void
     */
    Mono<Void> updateOrderStatus(Long orderId, Integer status);

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    Mono<Order> findById(Long id);

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    Mono<Order> findByOrderNo(String orderNo);

    /**
     * 根据用户ID查询订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    Flux<Order> findByUserId(String userId);

    /**
     * 查询桌号的订单列表
     */
    Flux<Order> findByTableNo(String tableNo);

    /**
     * 检查商品规格库存
     * @param itemId 商品ID
     * @param specIds 规格ID组合
     * @param optionIds 选项ID组合
     * @param quantity 数量
     * @return 是否足够
     */
    Mono<Boolean> checkStock(String itemId, String specIds, String optionIds, Integer quantity);

    /**
     * 扣减商品规格库存
     * @param itemId 商品ID
     * @param specIds 规格ID组合
     * @param optionIds 选项ID组合
     * @param quantity 数量
     * @return 是否成功
     */
    Mono<Boolean> reduceStock(String itemId, String specIds, String optionIds, Integer quantity);
} 