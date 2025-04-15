package com.example.dining.controller;

import com.example.dining.common.Result;
import com.example.dining.dto.AdminLoginDTO;
import com.example.dining.dto.AdminTokenDTO;
import com.example.dining.dto.AdminDTO;
import com.example.dining.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<AdminTokenDTO> login(@Validated @RequestBody AdminLoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @GetMapping("/info")
    public Result<AdminDTO> getAdminInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return Result.success(authService.getAdminInfo(username));
    }

    @PostMapping("/refresh")
    public Result<AdminTokenDTO> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return Result.success(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        authService.logout(username);
        return Result.success();
    }
}