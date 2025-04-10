package com.example.dining.service;

import com.example.dining.entity.PrinterConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PrinterConfigService {
    /**
     * 创建打印机配置
     * @param config 打印机配置
     * @return 创建的配置
     */
    Mono<PrinterConfig> createConfig(PrinterConfig config);

    /**
     * 更新打印机配置
     * @param config 打印机配置
     * @return 更新后的配置
     */
    Mono<PrinterConfig> updateConfig(PrinterConfig config);

    /**
     * 根据ID查询配置
     * @param id 配置ID
     * @return 配置信息
     */
    Mono<PrinterConfig> findById(Long id);

    /**
     * 根据类型查询配置
     * @param type 打印机类型
     * @return 配置列表
     */
    Flux<PrinterConfig> findByType(String type);

    /**
     * 查询所有启用的配置
     * @return 配置列表
     */
    Flux<PrinterConfig> findAllEnabled();

    /**
     * 更新配置状态
     * @param id 配置ID
     * @param status 状态
     * @return void
     */
    Mono<Void> updateStatus(Long id, Integer status);
} 