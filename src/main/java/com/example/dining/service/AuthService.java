package com.example.dining.service;

import com.example.dining.dto.AdminLoginDTO;
import com.example.dining.dto.AdminTokenDTO;
import com.example.dining.dto.AdminDTO;

public interface AuthService {
    /**
     * 管理员登录
     * @param loginDTO 登录参数
     * @return Token信息
     */
    AdminTokenDTO login(AdminLoginDTO loginDTO);

    /**
     * 获取管理员详情
     * @param username 用户名
     * @return 管理员信息
     */
    AdminDTO getAdminInfo(String username);

    /**
     * 刷新Token
     * @param refreshToken 刷新Token
     * @return 新的Token信息
     */
    AdminTokenDTO refreshToken(String refreshToken);

    /**
     * 登出
     * @param username 用户名
     */
    void logout(String username);
}