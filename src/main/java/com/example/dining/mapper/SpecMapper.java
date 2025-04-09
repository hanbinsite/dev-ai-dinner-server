package com.example.dining.mapper;

import com.example.dining.entity.Spec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecMapper {
    // 新增规格
    int insert(Spec spec);
    
    // 更新规格
    int update(Spec spec);
    
    // 删除规格
    int deleteById(@Param("id") String id);
    
    // 根据ID查询规格
    Spec selectById(@Param("id") String id);
    
    // 查询所有规格
    List<Spec> selectAll();
    
    // 根据商品ID查询规格
    List<Spec> selectByItemId(@Param("itemId") String itemId);

    @Select("SELECT * FROM spec WHERE id = #{id}")
    Spec findById(@Param("id") Long id);

    @Select("SELECT * FROM spec")
    List<Spec> findAll();

    @Select("SELECT * FROM spec WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Spec> findByName(@Param("name") String name);
} 