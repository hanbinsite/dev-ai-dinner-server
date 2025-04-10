package com.example.dining.printer.feie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FeiePrinterUtil {
    private static final String API_URL = "http://api.feieyun.cn/Api/Open/";
    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 生成签名
     */
    private static String generateSign(String user, String ukey, String timestamp) {
        try {
            String content = user + ukey + timestamp;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("生成签名失败", e);
            return null;
        }
    }

    /**
     * 打印订单
     */
    public static boolean printOrder(FeiePrinterConfig config, String content) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String sign = generateSign(config.getUser(), config.getUkey(), timestamp);

            Map<String, String> params = new HashMap<>();
            params.put("user", config.getUser());
            params.put("stime", timestamp);
            params.put("sig", sign);
            params.put("apiname", "Open_printMsg");
            params.put("sn", config.getSn());
            params.put("content", content);
            params.put("times", String.valueOf(config.getCopies()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(API_URL, request, String.class);

            JSONObject result = JSON.parseObject(response);
            return "0".equals(result.getString("ret"));
        } catch (Exception e) {
            log.error("打印订单失败", e);
            return false;
        }
    }

    /**
     * 查询打印机状态
     */
    public static boolean checkPrinterStatus(FeiePrinterConfig config) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String sign = generateSign(config.getUser(), config.getUkey(), timestamp);

            Map<String, String> params = new HashMap<>();
            params.put("user", config.getUser());
            params.put("stime", timestamp);
            params.put("sig", sign);
            params.put("apiname", "Open_queryPrinterStatus");
            params.put("sn", config.getSn());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(API_URL, request, String.class);

            JSONObject result = JSON.parseObject(response);
            return "0".equals(result.getString("ret")) && "在线".equals(result.getString("data"));
        } catch (Exception e) {
            log.error("查询打印机状态失败", e);
            return false;
        }
    }
} 