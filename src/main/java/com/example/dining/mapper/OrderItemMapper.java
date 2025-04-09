package com.example.dining.mapper;

import com.example.dining.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO order_item (order_id, item_id, spec_id, quantity, price, amount, remark) " +
            "VALUES (#{orderId}, #{itemId}, #{specId}, #{quantity}, #{price}, #{amount}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(OrderItem orderItem);

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Select("SELECT * FROM order_item WHERE id = #{id}")
    OrderItem findById(@Param("id") Long id);
} 