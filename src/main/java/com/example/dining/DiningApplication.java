package com.example.dining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    RedisAutoConfiguration.class
})
@EnableCaching
@Slf4j
public class DiningApplication {
    public static void main(String[] args) {
        log.info("Starting application...");
        SpringApplication.run(DiningApplication.class, args);
    }
}