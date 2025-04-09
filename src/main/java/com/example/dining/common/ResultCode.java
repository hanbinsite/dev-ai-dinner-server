package com.example.dining.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    
    // 业务错误码
    ITEM_NOT_FOUND(1001, "商品不存在"),
    SPEC_NOT_FOUND(1002, "规格不存在"),
    CATEGORY_NOT_FOUND(1003, "分类不存在"),
    ITEM_SPEC_NOT_FOUND(1004, "商品规格关联不存在");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
} 