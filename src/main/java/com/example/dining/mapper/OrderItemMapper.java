package com.example.dining.mapper;

import com.example.dining.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO order_item (order_id, item_id, spec_ids, option_ids, quantity, price, amount, remark, create_time, update_time) " +
            "VALUES (#{orderId}, #{itemId}, #{specIds}, #{optionIds}, #{quantity}, #{price}, #{amount}, #{remark}, #{createTime}, #{updateTime})")
    void insert(OrderItem orderItem);

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(Long orderId);

    @Select("SELECT * FROM order_item WHERE id = #{id}")
    OrderItem findById(Long id);
} 