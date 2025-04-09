package com.example.dining.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemDTO {
    @NotBlank(message = "商品名称不能为空")
    private String name;
    
    @NotBlank(message = "商品描述不能为空")
    private String description;
    
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;
    
    @NotBlank(message = "商品分类不能为空")
    private String categoryId;
    
    private String imageUrl;
    private Integer status = 1; // 默认上架
} 