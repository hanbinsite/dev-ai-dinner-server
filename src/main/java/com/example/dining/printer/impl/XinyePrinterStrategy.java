package com.example.dining.printer.impl;

import com.alibaba.fastjson.JSON;
import com.example.dining.entity.Order;
import com.example.dining.entity.OrderItem;
import com.example.dining.entity.PrinterConfig;
import com.example.dining.printer.PrinterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 芯烨云打印机策略
 */
@Slf4j
@Component
public class XinyePrinterStrategy implements PrinterStrategy {
    private static final String API_URL = "https://open.xpyun.net/api/openapi";
    private final RestTemplate restTemplate;

    public XinyePrinterStrategy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean print(Order order) {
        try {
            // 构建打印内容
            String content = buildPrintContent(order);
            
            // 调用芯烨云打印接口
            Map<String, Object> params = new HashMap<>();
            params.put("sn", order.getTableNo()); // 使用桌号作为打印机编号
            params.put("content", content);
            params.put("times", 1); // 打印份数
            
            String response = restTemplate.postForObject(API_URL + "/xprinter/print", params, String.class);
            Map<String, Object> result = JSON.parseObject(response, Map.class);
            
            return "0".equals(result.get("code"));
        } catch (Exception e) {
            log.error("芯烨云打印订单失败", e);
            return false;
        }
    }

    @Override
    public boolean test(PrinterConfig config) {
        try {
            // 构建测试内容
            String content = buildTestContent();
            
            // 调用芯烨云打印接口
            Map<String, Object> params = new HashMap<>();
            params.put("sn", config.getSn());
            params.put("content", content);
            params.put("times", 1);
            
            String response = restTemplate.postForObject(API_URL + "/xprinter/print", params, String.class);
            Map<String, Object> result = JSON.parseObject(response, Map.class);
            
            return "0".equals(result.get("code"));
        } catch (Exception e) {
            log.error("芯烨云打印机测试失败", e);
            return false;
        }
    }

    @Override
    public String getType() {
        return "XINYE";
    }

    /**
     * 构建打印内容
     */
    private String buildPrintContent(Order order) {
        StringBuilder content = new StringBuilder();
        
        // 打印头部
        content.append("<center>").append(order.getTableNo()).append("号桌订单</center>\n");
        content.append("订单号：").append(order.getOrderNo()).append("\n");
        content.append("下单时间：").append(formatDateTime(order.getCreateTime())).append("\n");
        content.append("--------------------------------\n");
        
        // 打印订单项
        for (OrderItem item : order.getOrderItems()) {
            content.append(item.getItemName()).append("\n");
            content.append("规格：").append(item.getSpecName()).append("\n");
            content.append("数量：").append(item.getQuantity()).append("  ");
            content.append("单价：").append(formatMoney(item.getPrice())).append("\n");
            content.append("小计：").append(formatMoney(item.getAmount())).append("\n");
            content.append("--------------------------------\n");
        }
        
        // 打印底部
        content.append("合计：").append(formatMoney(order.getTotalAmount())).append("\n");
        content.append("备注：").append(order.getRemark()).append("\n");
        content.append("\n\n\n"); // 切纸
        
        return content.toString();
    }

    /**
     * 构建测试内容
     */
    private String buildTestContent() {
        StringBuilder content = new StringBuilder();
        content.append("<center>打印机测试</center>\n");
        content.append("测试时间：").append(formatDateTime(LocalDateTime.now())).append("\n");
        content.append("--------------------------------\n");
        content.append("打印机状态：正常\n");
        content.append("--------------------------------\n");
        content.append("\n\n\n"); // 切纸
        return content.toString();
    }

    /**
     * 格式化日期时间
     */
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 格式化金额
     */
    private String formatMoney(BigDecimal amount) {
        return "¥" + amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
} 