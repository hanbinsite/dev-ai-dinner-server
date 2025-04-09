package com.example.dining.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderNoGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_LENGTH = 16;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成32位订单号
     * 格式：年月日时分秒(14位) + 随机字符(16位) + 校验位(2位)
     * 示例：20240315123456ABCDEFGHIJKLMNOP12
     */
    public static String generateOrderNo() {
        // 生成时间戳部分
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        
        // 生成随机部分
        String randomPart = generateRandomString(RANDOM_LENGTH);
        
        // 生成校验位
        String checkDigit = generateCheckDigit(timestamp + randomPart);
        
        return timestamp + randomPart + checkDigit;
    }

    /**
     * 生成指定长度的随机字符串
     */
    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成两位校验位
     * 使用简单的算法：将字符串的每个字符的ASCII码相加，然后取模36，转换为字符
     */
    private static String generateCheckDigit(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c;
        }
        // 生成两个校验位
        char check1 = CHARACTERS.charAt(sum % CHARACTERS.length());
        char check2 = CHARACTERS.charAt((sum * 2) % CHARACTERS.length());
        return String.valueOf(check1) + check2;
    }

    /**
     * 验证订单号是否有效
     */
    public static boolean validateOrderNo(String orderNo) {
        if (orderNo == null || orderNo.length() != 32) {
            return false;
        }
        
        String timestamp = orderNo.substring(0, 14);
        String randomPart = orderNo.substring(14, 30);
        String checkDigit = orderNo.substring(30);
        
        String calculatedCheckDigit = generateCheckDigit(timestamp + randomPart);
        return checkDigit.equals(calculatedCheckDigit);
    }
} 