package com.example.dining.dto;

import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemSpecDTO {
    @NotEmpty(message = "规格ID列表不能为空")
    private List<Long> specIds;        // 规格ID列表

    @NotEmpty(message = "规格选项ID列表不能为空")
    private List<Long> optionIds;      // 规格选项ID列表

    @NotNull(message = "价格不能为空")
    private BigDecimal price;          // 组合价格

    @NotNull(message = "库存不能为空")
    private Integer stock;             // 库存

    private Integer status = 1;        // 状态：0-下架 1-上架
    private String remark;             // 备注
} 