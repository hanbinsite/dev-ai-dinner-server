package com.example.dining.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
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
    
    @NotBlank(message = "商品图片不能为空")
    private String image;
    
    private Integer status = 1; // 默认上架
} 