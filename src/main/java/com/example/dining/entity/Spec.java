package com.example.dining.entity;

import lombok.Data;
import java.util.List;

@Data
public class Spec {
    private String id;
    private String name;
    private List<SpecOption> options;
} 