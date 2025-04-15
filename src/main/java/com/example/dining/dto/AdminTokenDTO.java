package com.example.dining.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AdminTokenDTO {
    private String accessToken;
    private String refreshToken;
    private Long expireTime;
    private AdminDTO adminInfo;
}