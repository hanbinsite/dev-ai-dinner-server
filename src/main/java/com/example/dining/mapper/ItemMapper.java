package com.example.dining.mapper;

import com.example.dining.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ItemMapper {
    // 新增商品
    int insert(Item item);
    
    // 更新商品
    int update(Item item);
    
    // 删除商品
    int deleteById(@Param("id") String id);
    
    // 根据ID查询商品
    Item selectById(@Param("id") String id);
    
    // 查询所有商品
    List<Item> selectAll();
    
    // 根据分类ID查询商品
    List<Item> selectByCategoryId(@Param("categoryId") String categoryId);
    
    // 查询热门商品
    List<Item> selectHotItems();
} 