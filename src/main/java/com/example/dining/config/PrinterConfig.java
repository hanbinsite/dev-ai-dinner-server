package com.example.dining.config;

import com.example.dining.printer.PrinterStrategy;
import com.example.dining.printer.PrinterStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 打印机配置类
 */
@Configuration
public class PrinterConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PrinterStrategyFactory printerStrategyFactory(List<PrinterStrategy> strategies) {
        PrinterStrategyFactory factory = new PrinterStrategyFactory();
        strategies.forEach(factory::registerStrategy);
        return factory;
    }
} 