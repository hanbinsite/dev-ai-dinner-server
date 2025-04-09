package com.example.dining.service;

import com.example.dining.entity.Table;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TableService {
    /**
     * 创建桌子
     */
    Mono<Table> createTable(Table table);

    /**
     * 更新桌子状态
     */
    Mono<Void> updateTableStatus(Long id, Integer status);

    /**
     * 更新桌子二维码
     */
    Mono<Void> updateTableQrCode(Long id, String qrCode);

    /**
     * 根据ID查询桌子
     */
    Mono<Table> findById(Long id);

    /**
     * 根据桌号查询桌子
     */
    Mono<Table> findByTableNo(String tableNo);

    /**
     * 查询所有桌子
     */
    Flux<Table> findAll();

    /**
     * 根据状态查询桌子
     */
    Flux<Table> findByStatus(Integer status);
} 