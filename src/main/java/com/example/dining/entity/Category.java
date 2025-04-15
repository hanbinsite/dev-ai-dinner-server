package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品分类实体类
 */
@Data
public class Category {
    private Long id;            // 分类ID
    private String name;        // 分类名称
    private String code;        // 分类编码
    private Integer sort;       // 排序
    private Integer status;     // 状态：0-禁用，1-启用
    private String remark;      // 备注
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
 