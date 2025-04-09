package com.example.dining.service.impl;

import com.example.dining.entity.Category;
import com.example.dining.entity.Item;
import com.example.dining.entity.Spec;
import com.example.dining.mapper.ItemMapper;
import com.example.dining.service.CategoryService;
import com.example.dining.service.ItemService;
import com.example.dining.service.SpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemMapper itemMapper;
    private final CategoryService categoryService;
    private final SpecService specService;
    
    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public void addItem(Item item) {
        itemMapper.insert(item);
        refreshCategoryItemsCache().subscribe();
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public void updateItem(Item item) {
        itemMapper.update(item);
        refreshCategoryItemsCache().subscribe();
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "categoryItems", allEntries = true)
    public void deleteItem(String id) {
        itemMapper.deleteById(id);
        refreshCategoryItemsCache().subscribe();
    }
    
    @Override
    public Item getItemById(String id) {
        return itemMapper.selectById(id);
    }
    
    @Override
    public List<Item> getAllItems() {
        return itemMapper.selectAll();
    }
    
    @Override
    public List<Item> getItemsByCategoryId(String categoryId) {
        return itemMapper.selectByCategoryId(categoryId);
    }
    
    @Override
    public List<Item> getHotItems() {
        return itemMapper.selectHotItems();
    }
    
    @Override
    @Cacheable(value = "categoryItems", key = "'all'")
    public Mono<Map<String, Object>> getCategoryItemsWithSpecs() {
        return categoryService.findAll()
                .collectList()
                .flatMap(categories -> {
                    List<Mono<Map<String, Object>>> categoryMonos = categories.stream()
                            .map(category -> getItemsWithSpecsForCategory(category))
                            .collect(Collectors.toList());

                    return Mono.zip(categoryMonos, results -> {
                        List<Map<String, Object>> categoryList = new ArrayList<>();
                        for (Object result : results) {
                            categoryList.add((Map<String, Object>) result);
                        }
                        Map<String, Object> response = new HashMap<>();
                        response.put("categories", categoryList);
                        return response;
                    });
                });
    }

    private Mono<Map<String, Object>> getItemsWithSpecsForCategory(Category category) {
        return itemMapper.findByCategoryId(category.getId())
                .collectList()
                .flatMap(items -> {
                    List<Mono<Map<String, Object>>> itemMonos = items.stream()
                            .map(item -> getItemWithSpecs(item))
                            .collect(Collectors.toList());

                    return Mono.zip(itemMonos, results -> {
                        List<Map<String, Object>> itemList = new ArrayList<>();
                        for (Object result : results) {
                            itemList.add((Map<String, Object>) result);
                        }
                        Map<String, Object> categoryMap = new HashMap<>();
                        categoryMap.put("id", category.getId());
                        categoryMap.put("name", category.getName());
                        categoryMap.put("items", itemList);
                        return categoryMap;
                    });
                });
    }

    private Mono<Map<String, Object>> getItemWithSpecs(Item item) {
        return specService.findByItemId(item.getId())
                .collectList()
                .map(specs -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("id", item.getId());
                    itemMap.put("name", item.getName());
                    itemMap.put("price", item.getPrice());
                    itemMap.put("description", item.getDescription());
                    itemMap.put("specs", specs.stream()
                            .map(spec -> {
                                Map<String, Object> specMap = new HashMap<>();
                                specMap.put("id", spec.getId());
                                specMap.put("name", spec.getName());
                                specMap.put("price", spec.getPrice());
                                return specMap;
                            })
                            .collect(Collectors.toList()));
                    return itemMap;
                });
    }

    @Override
    public Mono<Void> refreshCategoryItemsCache() {
        return getCategoryItemsWithSpecs()
                .then();
    }
} 