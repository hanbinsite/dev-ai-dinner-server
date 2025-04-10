package com.example.dining.service.impl;

import com.example.dining.entity.Order;
import com.example.dining.entity.OrderItem;
import com.example.dining.entity.PrinterConfig;
import com.example.dining.printer.PrinterStrategy;
import com.example.dining.printer.PrinterStrategyFactory;
import com.example.dining.printer.PrintContentBuilder;
import com.example.dining.service.PrinterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 打印机服务实现类
 */
@Slf4j
@Service
public class PrinterServiceImpl implements PrinterService {
    private final PrinterStrategyFactory printerStrategyFactory;
    private final PrintContentBuilder contentBuilder;

    public PrinterServiceImpl(PrinterStrategyFactory printerStrategyFactory) {
        this.printerStrategyFactory = printerStrategyFactory;
        this.contentBuilder = new PrintContentBuilder();
    }

    @Override
    public Mono<Boolean> printOrder(Order order) {
        return Mono.fromCallable(() -> {
            try {
                // 构建打印内容
                String content = buildOrderContent(order);
                
                // 获取打印机策略
                PrinterStrategy strategy = printerStrategyFactory.getStrategy(order.getPrinterType());
                if (strategy == null) {
                    log.error("不支持的打印机类型: {}", order.getPrinterType());
                    return false;
                }
                
                // 打印订单
                return strategy.print(content, order.getPrinterConfig());
            } catch (Exception e) {
                log.error("打印订单失败", e);
                return false;
            }
        });
    }

    @Override
    public Mono<Boolean> testPrinter(PrinterConfig config) {
        return Mono.fromCallable(() -> {
            try {
                // 构建测试内容
                String content = buildTestContent();
                
                // 获取打印机策略
                PrinterStrategy strategy = printerStrategyFactory.getStrategy(config.getType());
                if (strategy == null) {
                    log.error("不支持的打印机类型: {}", config.getType());
                    return false;
                }
                
                // 测试打印机
                return strategy.test(config);
            } catch (Exception e) {
                log.error("测试打印机失败", e);
                return false;
            }
        });
    }

    @Override
    public Flux<PrinterConfig> getEnabledPrinters() {
        return Flux.fromIterable(printerStrategyFactory.getAllStrategies().keySet())
                .map(type -> {
                    PrinterConfig config = new PrinterConfig();
                    config.setType(type);
                    return config;
                });
    }

    @Override
    public Flux<PrinterConfig> getPrintersByType(String type) {
        return Flux.just(type)
                .map(t -> {
                    PrinterConfig config = new PrinterConfig();
                    config.setType(t);
                    return config;
                });
    }

    /**
     * 构建订单打印内容
     */
    private String buildOrderContent(Order order) {
        contentBuilder.addTitle(order.getTableNo() + "号桌订单")
                .addOrderInfo(order.getOrderNo(), order.getCreateTime());
        
        for (OrderItem item : order.getOrderItems()) {
            contentBuilder.addOrderItem(
                item.getItemName(),
                item.getSpecName(),
                item.getQuantity(),
                item.getPrice(),
                item.getAmount()
            );
        }
        
        contentBuilder.addOrderTotal(order.getTotalAmount(), order.getRemark())
                .addCutPaper();
        
        return contentBuilder.build();
    }

    /**
     * 构建测试打印内容
     */
    private String buildTestContent() {
        return contentBuilder.addTitle("打印机测试")
                .addLine("测试时间：" + LocalDateTime.now())
                .addSeparator()
                .addLine("打印机状态：正常")
                .addSeparator()
                .addCutPaper()
                .build();
    }
} 