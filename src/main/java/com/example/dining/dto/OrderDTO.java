package com.example.dining.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    @NotBlank(message = "订单号不能为空")
    private String orderNo;
    
    @NotBlank(message = "桌号不能为空")
    private String tableNo;
    
    @NotNull(message = "订单金额不能为空")
    private BigDecimal amount;
    
    @NotEmpty(message = "订单项不能为空")
    @Valid
    private List<OrderItemDTO> items;
    
    @Data
    public static class OrderItemDTO {
        @NotBlank(message = "商品ID不能为空")
        private String itemId;
        
        @NotNull(message = "商品数量不能为空")
        private Integer quantity;
        
        @NotNull(message = "商品价格不能为空")
        private BigDecimal price;
        
        @NotNull(message = "商品总价不能为空")
        private BigDecimal amount;
    }
} 