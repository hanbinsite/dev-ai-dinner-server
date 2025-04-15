-- 创建角色权限关联表
CREATE TABLE role_permission (
    id BIGINT AUTO_INCREMENT COMMENT '关联ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_role_id (role_id),
    KEY idx_permission_id (permission_id),
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 创建管理员角色关联表
CREATE TABLE admin_role (
    id BIGINT AUTO_INCREMENT COMMENT '关联ID',
    admin_id BIGINT NOT NULL COMMENT '管理员ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_admin_id (admin_id),
    KEY idx_role_id (role_id),
    UNIQUE KEY uk_admin_role (admin_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员角色关联表'; 