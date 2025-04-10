package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;          // 订单ID
    private String itemId;         // 商品ID
    private String itemName;       // 商品名称
    private String specIds;        // 规格ID组合
    private String optionIds;      // 规格选项ID组合
    private String specName;       // 规格名称
    private Integer quantity;      // 数量
    private BigDecimal price;      // 单价
    private BigDecimal amount;     // 总价
    private String remark;         // 备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 