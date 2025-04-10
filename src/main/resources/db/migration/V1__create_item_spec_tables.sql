CREATE TABLE `item_spec` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `item_id` varchar(32) NOT NULL COMMENT '商品ID',
    `spec_ids` varchar(255) NOT NULL COMMENT '规格ID组合，格式：specId1,specId2,specId3',
    `option_ids` varchar(255) NOT NULL COMMENT '规格选项ID组合，格式：optionId1,optionId2,optionId3',
    `price` decimal(10,2) NOT NULL COMMENT '组合价格',
    `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
    `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-下架 1-上架',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_item_id` (`item_id`),
    UNIQUE KEY `uk_item_spec_options` (`item_id`, `spec_ids`, `option_ids`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格关联表'; 