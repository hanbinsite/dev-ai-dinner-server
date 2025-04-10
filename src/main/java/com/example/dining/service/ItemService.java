package com.example.dining.service;

import com.example.dining.entity.Category;
import com.example.dining.entity.Item;
import com.example.dining.entity.Spec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ItemService {
    /**
     * 查询所有商品
     */
    Flux<Item> findAll();

    /**
     * 根据分类ID查询商品
     */
    Flux<Item> findByCategoryId(String categoryId);

    /**
     * 根据ID查询商品
     */
    Mono<Item> findById(String id);

    /**
     * 搜索商品
     */
    Flux<Item> searchItems(String keyword);

    // 新增商品
    void addItem(Item item);
    
    // 更新商品
    void updateItem(Item item);
    
    // 删除商品
    void deleteItem(String id);
    
    // 查询所有商品
    List<Item> getAllItems();
    
    // 查询热门商品
    List<Item> getHotItems();

    /**
     * 获取所有分类及其商品和规格信息
     * @return 分类-商品-规格的嵌套结构
     */
    Mono<Map<String, Object>> getCategoryItemsWithSpecs();

    /**
     * 清除并重建分类商品规格缓存
     */
    Mono<Void> refreshCategoryItemsCache();
} 