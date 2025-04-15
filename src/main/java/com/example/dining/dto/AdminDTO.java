package com.example.dining.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminDTO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private String roleName;
}