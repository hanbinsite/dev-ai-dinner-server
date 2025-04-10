package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrinterConfig {
    private Long id;
    private String name;           // 打印机名称
    private String type;           // 打印机类型（FEIE, YLY等）
    private String sn;             // 打印机编号
    private String key;            // 打印机密钥
    private String config;         // 打印机配置（JSON格式）
    private Integer status;        // 状态：0-禁用，1-启用
    private String remark;         // 备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 