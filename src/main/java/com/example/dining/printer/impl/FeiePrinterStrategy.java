package com.example.dining.printer.impl;

import com.alibaba.fastjson.JSON;
import com.example.dining.entity.PrinterConfig;
import com.example.dining.printer.PrinterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 飞鹅打印机策略
 */
@Slf4j
@Component
public class FeiePrinterStrategy implements PrinterStrategy {
    private static final String API_URL = "http://api.feieyun.cn/Api/Open/";
    private final RestTemplate restTemplate;

    public FeiePrinterStrategy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean print(String content, PrinterConfig config) {
        try {
            // 调用飞鹅云打印接口
            Map<String, String> params = new HashMap<>();
            params.put("user", config.getUser());
            params.put("stime", String.valueOf(System.currentTimeMillis() / 1000));
            params.put("sig", generateSign(config));
            params.put("apiname", "Open_printMsg");
            params.put("sn", config.getSn());
            params.put("content", content);
            params.put("times", "1");
            
            String response = restTemplate.postForObject(API_URL, params, String.class);
            Map<String, Object> result = JSON.parseObject(response, Map.class);
            
            return "0".equals(result.get("ret"));
        } catch (Exception e) {
            log.error("飞鹅云打印失败", e);
            return false;
        }
    }

    @Override
    public boolean test(PrinterConfig config) {
        try {
            // 调用飞鹅云查询打印机状态接口
            Map<String, String> params = new HashMap<>();
            params.put("user", config.getUser());
            params.put("stime", String.valueOf(System.currentTimeMillis() / 1000));
            params.put("sig", generateSign(config));
            params.put("apiname", "Open_queryPrinterStatus");
            params.put("sn", config.getSn());
            
            String response = restTemplate.postForObject(API_URL, params, String.class);
            Map<String, Object> result = JSON.parseObject(response, Map.class);
            
            return "0".equals(result.get("ret"));
        } catch (Exception e) {
            log.error("飞鹅云打印机测试失败", e);
            return false;
        }
    }

    @Override
    public String getType() {
        return "FEIE";
    }

    /**
     * 生成签名
     */
    private String generateSign(PrinterConfig config) {
        String str = config.getUser() + config.getUkey() + String.valueOf(System.currentTimeMillis() / 1000);
        return MD5Util.encode(str);
    }
} 