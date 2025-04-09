package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Table {
    private Long id;
    private String tableNo;        // 桌号
    private String qrCode;         // 二维码URL
    private Integer capacity;      // 座位数
    private Integer status;        // 状态：0-空闲 1-使用中 2-已预订
    private String remark;         // 备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 