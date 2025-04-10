package com.example.dining.printer;

import com.example.dining.entity.PrinterConfig;

/**
 * 打印机策略接口
 */
public interface PrinterStrategy {
    /**
     * 打印内容
     * @param content 打印内容
     * @param printerConfig 打印机配置
     * @return 是否打印成功
     */
    boolean print(String content, PrinterConfig printerConfig);

    /**
     * 测试打印机
     * @param printerConfig 打印机配置
     * @return 是否测试成功
     */
    boolean test(PrinterConfig printerConfig);

    /**
     * 获取打印机类型
     */
    String getType();
} 