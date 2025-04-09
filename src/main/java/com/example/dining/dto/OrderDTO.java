package com.example.dining.dto;

import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    
    @NotBlank(message = "桌号不能为空")
    private String tableNo;
    
    @NotNull(message = "订单总金额不能为空")
    private BigDecimal totalAmount;
    
    @NotEmpty(message = "订单项不能为空")
    @Valid
    private List<OrderItemDTO> orderItems;
    
    private String remark;
}

@Data
class OrderItemDTO {
    @NotBlank(message = "商品ID不能为空")
    private String itemId;
    
    private Long specId;
    
    @NotNull(message = "商品数量不能为空")
    private Integer quantity;
    
    @NotNull(message = "商品单价不能为空")
    private BigDecimal price;
    
    @NotNull(message = "商品总价不能为空")
    private BigDecimal amount;
    
    private String remark;
} 