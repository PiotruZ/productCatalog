package com.example.productcatalog.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        ProducerResponse producer,
        Map<String, Object> attributes
) {}
