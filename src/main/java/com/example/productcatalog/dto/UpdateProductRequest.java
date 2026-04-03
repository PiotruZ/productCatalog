package com.example.productcatalog.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.Map;

public record UpdateProductRequest(
        String name,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,
        Long producerId,
        Map<String, Object> attributes
) {}
