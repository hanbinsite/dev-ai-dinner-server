package com.example.dining.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloController {
    
    @GetMapping("/hello")
    public String hello() {
        log.error("Received request for /hello");
        return "Hello, Spring Boot!";
    }
} 