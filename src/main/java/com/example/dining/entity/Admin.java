package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员实体类
 */
@Data
public class Admin {
    private Long id;                // 管理员ID
    private String username;        // 用户名
    private String password;        // 加密后的密码
    private String salt;            // 密码盐值
    private String role;            // 角色
    private int status;          // 状态：0-禁用，1-启用
    private String remark;          // 备注
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
} 