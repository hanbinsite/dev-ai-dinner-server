package com.example.dining.service;

import com.example.dining.entity.Order;
import com.example.dining.entity.PrinterConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PrinterService {
    /**
     * 打印订单
     * @param order 订单信息
     * @return 是否打印成功
     */
    Mono<Boolean> printOrder(Order order);

    /**
     * 测试打印机
     * @param config 打印机配置
     * @return 是否测试成功
     */
    Mono<Boolean> testPrinter(PrinterConfig config);

    /**
     * 获取所有启用的打印机配置
     * @return 打印机配置列表
     */
    Flux<PrinterConfig> getEnabledPrinters();

    /**
     * 根据类型获取打印机配置
     * @param type 打印机类型
     * @return 打印机配置列表
     */
    Flux<PrinterConfig> getPrintersByType(String type);
} 