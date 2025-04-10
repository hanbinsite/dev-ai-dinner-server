package com.example.dining.printer;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 打印机策略工厂
 */
@Component
public class PrinterStrategyFactory {
    private final Map<String, PrinterStrategy> strategyMap = new ConcurrentHashMap<>();

    /**
     * 注册打印机策略
     */
    public void registerStrategy(PrinterStrategy strategy) {
        strategyMap.put(strategy.getType(), strategy);
    }

    /**
     * 获取打印机策略
     */
    public PrinterStrategy getStrategy(String type) {
        return strategyMap.get(type);
    }

    /**
     * 获取所有打印机策略
     */
    public Map<String, PrinterStrategy> getAllStrategies() {
        return strategyMap;
    }
} 