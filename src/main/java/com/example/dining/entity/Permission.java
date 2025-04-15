package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 权限实体类
 */
@Data
public class Permission {
    private Long id;            // 权限ID
    private String name;        // 权限名称
    private String code;        // 权限编码
    private String type;        // 权限类型：menu-菜单，button-按钮
    private String url;         // 权限URL
    private Long parentId;      // 父权限ID
    private Integer sort;       // 排序
    private String icon;        // 图标
    private String remark;      // 备注
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
} 