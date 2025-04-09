package com.example.dining.mapper;

import com.example.dining.entity.ItemSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemSpecMapper {
    // 新增商品规格关联
    int insert(ItemSpec itemSpec);
    
    // 删除商品规格关联
    int deleteById(@Param("id") String id);
    
    // 根据商品ID删除关联
    int deleteByItemId(@Param("itemId") String itemId);
    
    // 根据规格ID删除关联
    int deleteBySpecId(@Param("specId") String specId);
    
    // 根据商品ID查询关联
    List<ItemSpec> selectByItemId(@Param("itemId") String itemId);
    
    // 根据规格ID查询关联
    List<ItemSpec> selectBySpecId(@Param("specId") String specId);

    @Select("SELECT * FROM item_spec WHERE id = #{id}")
    ItemSpec findById(@Param("id") Long id);

    @Select("SELECT * FROM item_spec WHERE item_id = #{itemId}")
    List<ItemSpec> findByItemId(@Param("itemId") String itemId);

    @Select("SELECT * FROM item_spec WHERE spec_id = #{specId}")
    List<ItemSpec> findBySpecId(@Param("specId") Long specId);
} 