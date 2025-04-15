package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员角色关联实体类
 */
@Data
public class AdminRole {
    private Long id;            // 关联ID
    private Long adminId;       // 管理员ID
    private Long roleId;        // 角色ID
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
} 