package com.example.dining.service.impl;

import com.example.dining.entity.PrinterConfig;
import com.example.dining.mapper.PrinterConfigMapper;
import com.example.dining.service.PrinterConfigService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PrinterConfigServiceImpl implements PrinterConfigService {
    private final PrinterConfigMapper printerConfigMapper;

    public PrinterConfigServiceImpl(PrinterConfigMapper printerConfigMapper) {
        this.printerConfigMapper = printerConfigMapper;
    }

    @Override
    public Mono<PrinterConfig> createConfig(PrinterConfig config) {
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        printerConfigMapper.insert(config);
        return Mono.just(config);
    }

    @Override
    public Mono<PrinterConfig> updateConfig(PrinterConfig config) {
        config.setUpdateTime(LocalDateTime.now());
        printerConfigMapper.update(config);
        return Mono.just(config);
    }

    @Override
    public Mono<PrinterConfig> findById(Long id) {
        return Mono.justOrEmpty(printerConfigMapper.findById(id));
    }

    @Override
    public Flux<PrinterConfig> findByType(String type) {
        return Flux.fromIterable(printerConfigMapper.findByType(type));
    }

    @Override
    public Flux<PrinterConfig> findAllEnabled() {
        return Flux.fromIterable(printerConfigMapper.findAllEnabled());
    }

    @Override
    public Mono<Void> updateStatus(Long id, Integer status) {
        printerConfigMapper.updateStatus(id, status, LocalDateTime.now());
        return Mono.empty();
    }
} 