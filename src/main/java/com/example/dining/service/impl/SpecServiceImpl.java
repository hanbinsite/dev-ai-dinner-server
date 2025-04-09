package com.example.dining.service.impl;

import com.example.dining.entity.Spec;
import com.example.dining.mapper.SpecMapper;
import com.example.dining.service.ItemService;
import com.example.dining.service.SpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SpecServiceImpl implements SpecService {
    private final SpecMapper specMapper;
    private final ItemService itemService;

    @Override
    public Mono<Spec> findById(Long id) {
        return Mono.fromCallable(() -> specMapper.findById(id));
    }

    @Override
    public Flux<Spec> findAll() {
        return Flux.fromIterable(specMapper.findAll());
    }

    @Override
    public Flux<Spec> findByName(String name) {
        return Flux.fromIterable(specMapper.findByName(name));
    }

    @Override
    public Flux<Spec> findByItemId(String itemId) {
        return Flux.fromIterable(specMapper.selectByItemId(itemId));
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> addSpec(Spec spec) {
        return Mono.fromRunnable(() -> specMapper.insert(spec))
                .then(itemService.refreshCategoryItemsCache());
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> updateSpec(Spec spec) {
        return Mono.fromRunnable(() -> specMapper.update(spec))
                .then(itemService.refreshCategoryItemsCache());
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> deleteSpec(Long id) {
        return Mono.fromRunnable(() -> specMapper.deleteById(id))
                .then(itemService.refreshCategoryItemsCache());
    }
} 