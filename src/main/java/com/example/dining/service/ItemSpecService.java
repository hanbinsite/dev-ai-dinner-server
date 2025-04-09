package com.example.dining.service;

import com.example.dining.entity.ItemSpec;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemSpecService {
    // 新增商品规格关联
    Mono<Void> addItemSpec(ItemSpec itemSpec);
    
    // 更新商品规格关联
    Mono<Void> updateItemSpec(ItemSpec itemSpec);
    
    // 删除商品规格关联
    Mono<Void> deleteItemSpec(Long id);
    
    // 根据商品ID删除关联
    void deleteByItemId(String itemId);
    
    // 根据规格ID删除关联
    void deleteBySpecId(String specId);
    
    // 根据商品ID查询关联
    List<ItemSpec> getByItemId(String itemId);
    
    // 根据规格ID查询关联
    List<ItemSpec> getBySpecId(String specId);

    Mono<ItemSpec> findById(Long id);
    Flux<ItemSpec> findByItemId(String itemId);
    Flux<ItemSpec> findBySpecId(Long specId);
} 