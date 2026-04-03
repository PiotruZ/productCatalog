package com.example.productcatalog.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateProducerRequest(
        @NotBlank(message = "Producer name is required")
        String name,
        String country
) {}