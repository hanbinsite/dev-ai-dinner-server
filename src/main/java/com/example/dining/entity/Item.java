package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Item {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private Long categoryId;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 