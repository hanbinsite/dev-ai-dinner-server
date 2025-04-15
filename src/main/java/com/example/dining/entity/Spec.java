package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Spec {
    private Long id;
    private String name;
    private String value;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 