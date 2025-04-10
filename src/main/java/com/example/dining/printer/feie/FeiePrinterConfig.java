package com.example.dining.printer.feie;

import lombok.Data;

@Data
public class FeiePrinterConfig {
    private String user;           // 飞鹅云后台注册用户名
    private String ukey;           // 飞鹅云后台注册账号后生成的UKEY
    private String sn;             // 打印机编号
    private String key;            // 打印机密钥
    private Integer copies;        // 打印份数
    private String template;       // 打印模板
} 