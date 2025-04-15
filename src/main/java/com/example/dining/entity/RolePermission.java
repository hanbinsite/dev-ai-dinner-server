package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RolePermission {
    private Long id;
    private Long roleId;
    private Long permissionId;
    private LocalDateTime createTime;
} 