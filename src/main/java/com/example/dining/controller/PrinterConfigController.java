package com.example.dining.controller;

import com.example.dining.common.Result;
import com.example.dining.entity.PrinterConfig;
import com.example.dining.service.PrinterConfigService;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/printer-config")
public class PrinterConfigController {
    private final PrinterConfigService printerConfigService;

    public PrinterConfigController(PrinterConfigService printerConfigService) {
        this.printerConfigService = printerConfigService;
    }

    @PostMapping
    public Mono<Result<PrinterConfig>> createConfig(@RequestBody PrinterConfig config) {
        return printerConfigService.createConfig(config)
                .map(Result::success);
    }

    @PutMapping("/{id}")
    public Mono<Result<PrinterConfig>> updateConfig(@PathVariable Long id, @RequestBody PrinterConfig config) {
        config.setId(id);
        return printerConfigService.updateConfig(config)
                .map(Result::success);
    }

    @GetMapping("/{id}")
    public Mono<Result<PrinterConfig>> getConfig(@PathVariable Long id) {
        return printerConfigService.findById(id)
                .map(Result::success);
    }

    @GetMapping("/type/{type}")
    public Flux<Result<PrinterConfig>> getConfigsByType(@PathVariable String type) {
        return printerConfigService.findByType(type)
                .map(Result::success);
    }

    @GetMapping("/enabled")
    public Flux<Result<PrinterConfig>> getEnabledConfigs() {
        return printerConfigService.findAllEnabled()
                .map(Result::success);
    }

    @PutMapping("/{id}/status")
    public Mono<Result<Void>> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        return printerConfigService.updateStatus(id, status)
                .then(Mono.just(Result.success()));
    }
} 