package com.example.dining.service.impl;

import com.example.dining.entity.ItemSpec;
import com.example.dining.mapper.ItemSpecMapper;
import com.example.dining.service.ItemService;
import com.example.dining.service.ItemSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSpecServiceImpl implements ItemSpecService {
    
    private final ItemSpecMapper itemSpecMapper;
    private final ItemService itemService;
    
    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> addItemSpec(ItemSpec itemSpec) {
        return Mono.fromRunnable(() -> itemSpecMapper.insert(itemSpec))
                .then(itemService.refreshCategoryItemsCache());
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> deleteItemSpec(Long id) {
        return Mono.fromRunnable(() -> itemSpecMapper.deleteById(id))
                .then(itemService.refreshCategoryItemsCache());
    }
    
    @Override
    @Transactional
    public void deleteByItemId(String itemId) {
        itemSpecMapper.deleteByItemId(itemId);
    }
    
    @Override
    @Transactional
    public void deleteBySpecId(String specId) {
        itemSpecMapper.deleteBySpecId(specId);
    }
    
    @Override
    public List<ItemSpec> getByItemId(String itemId) {
        return itemSpecMapper.selectByItemId(itemId);
    }
    
    @Override
    public List<ItemSpec> getBySpecId(String specId) {
        return itemSpecMapper.selectBySpecId(specId);
    }

    @Override
    public Mono<ItemSpec> findById(Long id) {
        return Mono.fromCallable(() -> itemSpecMapper.findById(id));
    }

    @Override
    public Flux<ItemSpec> findByItemId(String itemId) {
        return Flux.fromIterable(itemSpecMapper.findByItemId(itemId));
    }

    @Override
    public Flux<ItemSpec> findBySpecId(Long specId) {
        return Flux.fromIterable(itemSpecMapper.findBySpecId(specId));
    }

    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public Mono<Void> updateItemSpec(ItemSpec itemSpec) {
        return Mono.fromRunnable(() -> itemSpecMapper.update(itemSpec))
                .then(itemService.refreshCategoryItemsCache());
    }
} 