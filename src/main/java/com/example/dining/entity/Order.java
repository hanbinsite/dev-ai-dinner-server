package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    private String orderNo;        // 订单号
    private String userId;         // 用户ID
    private String tableNo;        // 桌号
    private BigDecimal totalAmount; // 总金额
    private Integer status;        // 状态：0-待支付，1-已支付，2-已取消
    private String remark;         // 备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItem> orderItems; // 订单项
} 