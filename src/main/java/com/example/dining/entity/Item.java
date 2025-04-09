package com.example.dining.entity;

import lombok.Data;

@Data
public class Item {
    private String id;
    private String name;
    private String desc;
    private Double basePrice;
    private String image;
    private Boolean isHot;
} 