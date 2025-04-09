package com.example.dining.controller;

import com.example.dining.common.Result;
import com.example.dining.entity.Item;
import com.example.dining.service.ItemService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Flux<Result<Item>> getAllItems() {
        return itemService.findAll()
                .map(Result::success);
    }

    @GetMapping("/category/{categoryId}")
    public Flux<Result<Item>> getItemsByCategory(@PathVariable String categoryId) {
        return itemService.findByCategoryId(categoryId)
                .map(Result::success);
    }

    @GetMapping("/{id}")
    public Mono<Result<Item>> getItemById(@PathVariable String id) {
        return itemService.findById(id)
                .map(Result::success);
    }

    @GetMapping("/search")
    public Flux<Result<Item>> searchItems(@RequestParam String keyword) {
        return itemService.searchItems(keyword)
                .map(Result::success);
    }
} 