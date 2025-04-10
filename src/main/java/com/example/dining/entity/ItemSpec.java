package com.example.dining.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemSpec {
    private Long id;
    private String itemId;         // 商品ID
    private String specIds;        // 规格ID组合，格式：specId1,specId2,specId3
    private String optionIds;      // 规格选项ID组合，格式：optionId1,optionId2,optionId3
    private BigDecimal price;      // 组合价格
    private Integer stock;         // 库存
    private Integer status;        // 状态：0-下架 1-上架
    private String remark;         // 备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 