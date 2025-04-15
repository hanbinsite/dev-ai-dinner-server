package com.example.dining.mapper;

import com.example.dining.entity.Admin;
import org.apache.ibatis.annotations.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper
public interface AdminMapper {
    /**
     * 插入管理员
     * @param admin 管理员信息
     * @return 插入结果
     */
    @Insert("INSERT INTO admin (username, password, role, status, remark, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{role}, #{status}, #{remark}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Admin admin);

    /**
     * 更新管理员
     * @param admin 管理员信息
     * @return 更新结果
     */
    @Update("UPDATE admin SET " +
            "username = #{username}, " +
            "role = #{role}, " +
            "remark = #{remark}, " +
            "update_time = NOW() " +
            "WHERE id = #{id}")
    int update(Admin admin);

    /**
     * 删除管理员
     * @param id 管理员ID
     * @return 删除结果
     */
    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 查询所有管理员
     * @return 管理员列表
     */
    @Select("SELECT * FROM admin ORDER BY create_time DESC")
    Admin findAll();

    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 管理员信息
     */
    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin findById(Long id);

    /**
     * 根据用户名查询管理员
     * @param username 用户名
     * @return 管理员信息
     */
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(String username);
} 