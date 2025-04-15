package com.example.dining.service.impl;

import com.example.dining.dto.AdminDTO;
import com.example.dining.dto.AdminLoginDTO;
import com.example.dining.dto.AdminTokenDTO;
import com.example.dining.service.AuthService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    /**
     * 管理员登录
     *
     * @param loginDTO 登录参数
     * @return Token信息
     */
    @Override
    public AdminTokenDTO login(AdminLoginDTO loginDTO) {
        return null;
    }

    /**
     * 获取管理员详情
     *
     * @param username 用户名
     * @return 管理员信息
     */
    @Override
    public AdminDTO getAdminInfo(String username) {
        return null;
    }

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新Token
     * @return 新的Token信息
     */
    @Override
    public AdminTokenDTO refreshToken(String refreshToken) {
        return null;
    }

    /**
     * 登出
     *
     * @param username 用户名
     */
    @Override
    public void logout(String username) {

    }
}
