package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private String itemId;
    private Long specId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private String remark;
} 