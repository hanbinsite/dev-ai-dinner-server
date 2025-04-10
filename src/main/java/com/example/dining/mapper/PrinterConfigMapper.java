package com.example.dining.mapper;

import com.example.dining.entity.PrinterConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.time.LocalDateTime;

@Mapper
public interface PrinterConfigMapper {
    @Insert("INSERT INTO printer_config (name, type, sn, key, config, status, remark, create_time, update_time) " +
            "VALUES (#{name}, #{type}, #{sn}, #{key}, #{config}, #{status}, #{remark}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(PrinterConfig config);

    @Update("UPDATE printer_config SET name = #{name}, type = #{type}, sn = #{sn}, key = #{key}, " +
            "config = #{config}, status = #{status}, remark = #{remark}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    void update(PrinterConfig config);

    @Select("SELECT * FROM printer_config WHERE id = #{id}")
    PrinterConfig findById(Long id);

    @Select("SELECT * FROM printer_config WHERE type = #{type} AND status = 1")
    List<PrinterConfig> findByType(String type);

    @Select("SELECT * FROM printer_config WHERE status = 1")
    List<PrinterConfig> findAllEnabled();

    @Update("UPDATE printer_config SET status = #{status}, update_time = #{updateTime} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("updateTime") LocalDateTime updateTime);
} 