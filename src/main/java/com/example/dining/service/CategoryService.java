package com.example.dining.service;

import com.example.dining.entity.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Mono<Category> findById(Long id);
    Flux<Category> findAll();
    Flux<Category> findByName(String name);
} 