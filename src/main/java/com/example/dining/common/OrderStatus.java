package com.example.dining.common;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDERED(0, "已下单"),
    PAID(1, "已支付"),
    COMPLETED(2, "已完成");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status code: " + code);
    }
} 