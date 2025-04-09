package com.example.dining.service;

import com.example.dining.entity.Spec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpecService {
    // 新增规格
    Mono<Void> addSpec(Spec spec);
    
    // 更新规格
    Mono<Void> updateSpec(Spec spec);
    
    // 删除规格
    Mono<Void> deleteSpec(Long id);
    
    // 根据ID查询规格
    Spec getSpecById(String id);
    
    // 查询所有规格
    Flux<Spec> getAllSpecs();
    
    // 根据商品ID查询规格
    Flux<Spec> getSpecsByItemId(String itemId);

    Mono<Spec> findById(Long id);
    Flux<Spec> findAll();
    Flux<Spec> findByName(String name);
    Flux<Spec> findByItemId(String itemId);
} 