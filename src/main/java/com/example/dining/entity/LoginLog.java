package com.example.dining.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LoginLog {
    private Long id;
    private Long userId;
    private String username;
    private String ip;
    private String userAgent;
    private String status;
    private String message;
    private LocalDateTime createTime;
} 