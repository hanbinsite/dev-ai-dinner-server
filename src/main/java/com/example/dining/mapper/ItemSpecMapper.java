package com.example.dining.mapper;

import com.example.dining.entity.ItemSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemSpecMapper {
    @Insert("INSERT INTO item_spec (item_id, spec_ids, option_ids, price, stock, status, remark) " +
            "VALUES (#{itemId}, #{specIds}, #{optionIds}, #{price}, #{stock}, #{status}, #{remark})")
    void insert(ItemSpec itemSpec);

    @Update("UPDATE item_spec SET price = #{price}, stock = #{stock}, status = #{status}, remark = #{remark} " +
            "WHERE id = #{id}")
    void update(ItemSpec itemSpec);

    @Select("SELECT * FROM item_spec WHERE id = #{id}")
    ItemSpec findById(Long id);

    @Select("SELECT * FROM item_spec WHERE item_id = #{itemId}")
    List<ItemSpec> findByItemId(String itemId);

    @Select("SELECT * FROM item_spec WHERE item_id = #{itemId} AND status = 1")
    List<ItemSpec> findAvailableByItemId(String itemId);

    @Select("SELECT * FROM item_spec WHERE item_id = #{itemId} AND spec_ids = #{specIds} AND option_ids = #{optionIds}")
    ItemSpec findByItemIdAndSpecs(String itemId, String specIds, String optionIds);

    @Update("UPDATE item_spec SET stock = stock - #{quantity} WHERE id = #{id} AND stock >= #{quantity}")
    int reduceStock(@Param("id") Long id, @Param("quantity") int quantity);

    @Update("UPDATE item_spec SET stock = stock + #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("quantity") int quantity);
} 