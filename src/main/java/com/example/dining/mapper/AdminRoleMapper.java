package com.example.dining.mapper;

import com.example.dining.entity.AdminRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 管理员角色关联Mapper接口
 */
@Mapper
public interface AdminRoleMapper {
    
    /**
     * 插入管理员角色关联记录
     */
    @Insert("INSERT INTO admin_role(admin_id, role_id, create_time, update_time) " +
            "VALUES(#{adminId}, #{roleId}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(AdminRole adminRole);
    
    /**
     * 根据管理员ID删除关联记录
     */
    @Delete("DELETE FROM admin_role WHERE admin_id = #{adminId}")
    void deleteByAdminId(@Param("adminId") Long adminId);
    
    /**
     * 根据角色ID删除关联记录
     */
    @Delete("DELETE FROM admin_role WHERE role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据管理员ID查询角色ID列表
     */
    @Select("SELECT role_id FROM admin_role WHERE admin_id = #{adminId}")
    List<Long> findRoleIdsByAdminId(@Param("adminId") Long adminId);
    
    /**
     * 根据角色ID查询管理员ID列表
     */
    @Select("SELECT admin_id FROM admin_role WHERE role_id = #{roleId}")
    List<Long> findAdminIdsByRoleId(@Param("roleId") Long roleId);
} 