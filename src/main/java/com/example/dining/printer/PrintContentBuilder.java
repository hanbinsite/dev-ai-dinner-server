package com.example.dining.printer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 打印内容构建器
 */
public class PrintContentBuilder {
    private final StringBuilder content = new StringBuilder();
    private final String lineSeparator = "\n";
    private final String separator = "--------------------------------";

    /**
     * 添加标题
     */
    public PrintContentBuilder addTitle(String title) {
        content.append("<center>").append(title).append("</center>").append(lineSeparator);
        return this;
    }

    /**
     * 添加文本行
     */
    public PrintContentBuilder addLine(String text) {
        content.append(text).append(lineSeparator);
        return this;
    }

    /**
     * 添加分隔线
     */
    public PrintContentBuilder addSeparator() {
        content.append(separator).append(lineSeparator);
        return this;
    }

    /**
     * 添加订单信息
     */
    public PrintContentBuilder addOrderInfo(String orderNo, LocalDateTime createTime) {
        addLine("订单号：" + orderNo);
        addLine("下单时间：" + formatDateTime(createTime));
        addSeparator();
        return this;
    }

    /**
     * 添加订单项
     */
    public PrintContentBuilder addOrderItem(String itemName, String specName, 
                                          int quantity, BigDecimal price, BigDecimal amount) {
        addLine(itemName);
        addLine("规格：" + specName);
        addLine("数量：" + quantity + "  单价：" + formatMoney(price));
        addLine("小计：" + formatMoney(amount));
        addSeparator();
        return this;
    }

    /**
     * 添加订单合计
     */
    public PrintContentBuilder addOrderTotal(BigDecimal totalAmount, String remark) {
        addLine("合计：" + formatMoney(totalAmount));
        if (remark != null && !remark.isEmpty()) {
            addLine("备注：" + remark);
        }
        return this;
    }

    /**
     * 添加切纸指令
     */
    public PrintContentBuilder addCutPaper() {
        content.append(lineSeparator).append(lineSeparator).append(lineSeparator);
        return this;
    }

    /**
     * 构建打印内容
     */
    public String build() {
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