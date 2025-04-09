-- 创建订单表
CREATE TABLE IF NOT EXISTS `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号(32位)',
    `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
    `table_no` VARCHAR(32) NOT NULL COMMENT '桌号',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    `status` TINYINT NOT NULL DEFAULT '0' COMMENT '订单状态：0-已下单 1-已支付 2-已完成',
    `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_table_no` (`table_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 创建订单项表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `item_id` VARCHAR(32) NOT NULL COMMENT '商品ID',
    `spec_id` BIGINT COMMENT '规格ID',
    `quantity` INT NOT NULL COMMENT '数量',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `remark` VARCHAR(255) COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表'; 