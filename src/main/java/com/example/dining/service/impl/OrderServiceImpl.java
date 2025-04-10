package com.example.dining.service.impl;

import com.example.dining.common.OrderStatus;
import com.example.dining.util.OrderNoGenerator;
import com.example.dining.entity.Order;
import com.example.dining.entity.OrderItem;
import com.example.dining.entity.ItemSpec;
import com.example.dining.entity.PrinterConfig;
import com.example.dining.mapper.ItemSpecMapper;
import com.example.dining.mapper.OrderItemMapper;
import com.example.dining.mapper.OrderMapper;
import com.example.dining.printer.PrinterStrategy;
import com.example.dining.printer.PrinterStrategyFactory;
import com.example.dining.service.OrderService;
import com.example.dining.service.PrinterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ItemSpecMapper itemSpecMapper;
    private final PrinterStrategyFactory printerStrategyFactory;
    private final PrinterConfigService printerConfigService;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, 
                          OrderItemMapper orderItemMapper,
                          ItemSpecMapper itemSpecMapper,
                          PrinterStrategyFactory printerStrategyFactory,
                          PrinterConfigService printerConfigService) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.itemSpecMapper = itemSpecMapper;
        this.printerStrategyFactory = printerStrategyFactory;
        this.printerConfigService = printerConfigService;
    }

    @Override
    @Transactional
    public Mono<Order> createOrder(Order order) {
        // 生成订单号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(0); // 待支付
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        // 计算订单总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // 校验每个订单项
        for (OrderItem item : order.getOrderItems()) {
            // 获取商品规格信息
            ItemSpec itemSpec = itemSpecMapper.findByItemIdAndSpecs(item.getItemId(), item.getSpecIds(), item.getOptionIds());
            if (itemSpec == null) {
                return Mono.error(new RuntimeException("商品规格不存在"));
            }
            
            // 校验商品状态
            if (itemSpec.getStatus() != 1) {
                return Mono.error(new RuntimeException("商品已下架"));
            }
            
            // 校验库存
            if (itemSpec.getStock() < item.getQuantity()) {
                return Mono.error(new RuntimeException("商品库存不足"));
            }
            
            // 校验价格
            if (!itemSpec.getPrice().equals(item.getPrice())) {
                return Mono.error(new RuntimeException("商品价格已变更，请刷新后重试"));
            }
            
            // 计算订单项金额
            BigDecimal itemAmount = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            if (!itemAmount.equals(item.getAmount())) {
                return Mono.error(new RuntimeException("订单项金额计算错误"));
            }
            
            // 累加订单总金额
            totalAmount = totalAmount.add(itemAmount);
            
            // 扣减库存
            if (itemSpecMapper.reduceStock(itemSpec.getId(), item.getQuantity()) <= 0) {
                return Mono.error(new RuntimeException("扣减库存失败"));
            }
            
            // 设置订单项信息
            item.setOrderId(order.getId());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            orderItemMapper.insert(item);
        }
        
        // 校验订单总金额
        if (!totalAmount.equals(order.getTotalAmount())) {
            return Mono.error(new RuntimeException("订单总金额计算错误"));
        }
        
        // 保存订单
        orderMapper.insert(order);

        // 调用打印机打印订单
        try {
            // 获取所有启用的打印机配置
            printerConfigService.findAllEnabled()
                .subscribe(config -> {
                    try {
                        PrinterStrategy printerStrategy = printerStrategyFactory.getStrategy(config.getType());
                        if (!printerStrategy.print(order)) {
                            // 打印失败记录日志
                            // TODO: 记录打印失败日志
                        }
                    } catch (Exception e) {
                        // 打印异常记录日志
                        // TODO: 记录打印异常日志
                    }
                });
        } catch (Exception e) {
            // 打印异常记录日志，但不影响订单创建
            // TODO: 记录打印异常日志
        }

        return Mono.just(order);
    }

    @Override
    @Transactional
    public Mono<Void> updateOrderStatus(Long orderId, Integer status) {
        orderMapper.updateStatus(orderId, status);
        return Mono.empty();
    }

    @Override
    public Mono<Order> findById(Long id) {
        Order order = orderMapper.findById(id);
        if (order != null) {
            order.setOrderItems(orderItemMapper.findByOrderId(id));
        }
        return Mono.justOrEmpty(order);
    }

    @Override
    public Mono<Order> findByOrderNo(String orderNo) {
        Order order = orderMapper.findByOrderNo(orderNo);
        if (order != null) {
            order.setOrderItems(orderItemMapper.findByOrderId(order.getId()));
        }
        return Mono.justOrEmpty(order);
    }

    @Override
    public Flux<Order> findByUserId(String userId) {
        return Flux.fromIterable(orderMapper.findByUserId(userId))
                .map(order -> {
                    order.setOrderItems(orderItemMapper.findByOrderId(order.getId()));
                    return order;
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

    @Override
    public Mono<Boolean> checkStock(String itemId, String specIds, String optionIds, Integer quantity) {
        return Mono.fromCallable(() -> {
            var itemSpec = itemSpecMapper.findByItemIdAndSpecs(itemId, specIds, optionIds);
            return itemSpec != null && itemSpec.getStock() >= quantity;
        });
    }

    @Override
    @Transactional
    public Mono<Boolean> reduceStock(String itemId, String specIds, String optionIds, Integer quantity) {
        return Mono.fromCallable(() -> {
            var itemSpec = itemSpecMapper.findByItemIdAndSpecs(itemId, specIds, optionIds);
            if (itemSpec != null && itemSpec.getStock() >= quantity) {
                return itemSpecMapper.reduceStock(itemSpec.getId(), quantity) > 0;
            }
            return false;
        });
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 6);
    }
} 