package com.example.dining.service.impl;

import com.example.dining.entity.Category;
import com.example.dining.mapper.CategoryMapper;
import com.example.dining.service.CategoryService;
import com.example.dining.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final ItemService itemService;

    @Override
    public Mono<Category> findById(Long id) {
        return Mono.fromCallable(() -> categoryMapper.findById(id));
    }

    @Override
    public Flux<Category> findAll() {
        return Flux.fromIterable(categoryMapper.findAll());
    }

    @Override
    public Flux<Category> findByName(String name) {
        return Flux.fromIterable(categoryMapper.findByName(name));
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> addCategory(Category category) {
        return Mono.fromRunnable(() -> categoryMapper.insert(category))
                .then(itemService.refreshCategoryItemsCache());
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> updateCategory(Category category) {
        return Mono.fromRunnable(() -> categoryMapper.update(category))
                .then(itemService.refreshCategoryItemsCache());
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> deleteCategory(Long id) {
        return Mono.fromRunnable(() -> categoryMapper.deleteById(id))
                .then(itemService.refreshCategoryItemsCache());
    }
} 