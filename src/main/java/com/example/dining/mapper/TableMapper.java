package com.example.dining.mapper;

import com.example.dining.entity.Table;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TableMapper {
    @Insert("INSERT INTO `table` (table_no, qr_code, capacity, status, remark, create_time, update_time) " +
            "VALUES (#{tableNo}, #{qrCode}, #{capacity}, #{status}, #{remark}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Table table);

    @Update("UPDATE `table` SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Update("UPDATE `table` SET qr_code = #{qrCode}, update_time = NOW() WHERE id = #{id}")
    void updateQrCode(@Param("id") Long id, @Param("qrCode") String qrCode);

    @Select("SELECT * FROM `table` WHERE id = #{id}")
    Table findById(@Param("id") Long id);

    @Select("SELECT * FROM `table` WHERE table_no = #{tableNo}")
    Table findByTableNo(@Param("tableNo") String tableNo);

    @Select("SELECT * FROM `table` ORDER BY table_no")
    List<Table> findAll();

    @Select("SELECT * FROM `table` WHERE status = #{status} ORDER BY table_no")
    List<Table> findByStatus(@Param("status") Integer status);
} 