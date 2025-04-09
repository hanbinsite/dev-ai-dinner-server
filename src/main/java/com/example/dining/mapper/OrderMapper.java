package com.example.dining.mapper;

import com.example.dining.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `order` (order_no, user_id, table_no, total_amount, status, remark, create_time, update_time) " +
            "VALUES (#{orderNo}, #{userId}, #{tableNo}, #{totalAmount}, #{status}, #{remark}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    @Update("UPDATE `order` SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order findById(@Param("id") Long id);

    @Select("SELECT * FROM `order` WHERE order_no = #{orderNo}")
    Order findByOrderNo(@Param("orderNo") String orderNo);

    @Select("SELECT * FROM `order` WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Order> findByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM `order` WHERE table_no = #{tableNo} ORDER BY create_time DESC")
    List<Order> findByTableNo(@Param("tableNo") String tableNo);
} 